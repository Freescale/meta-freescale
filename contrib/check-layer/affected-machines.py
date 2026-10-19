#!/usr/bin/env python3
"""Pick the machines a yocto-check-layer run needs to exercise a change.

See README.md in this directory.
"""

import argparse
import fnmatch
import json
import os
import re
import subprocess
import sys
import tempfile
from collections import OrderedDict
from functools import lru_cache

LAYER = os.path.normpath(os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", ".."))

RECIPES = ("*.bb", "*.bbappend", "*.inc")

# Never parsed by BitBake: no machine needed.
NOT_PARSED = (".github/*", "contrib/*", "scripts/*", "*.md", "README*",
              "LICENSE*", "COPYING*", "SECURITY*", ".oelint*", "oelint.*",
              "wic/*", ".git*", ".editorconfig", "default-registry/*")

# Not parsed by this run: dynamic layers lack their other layer, and
# check-layer enables no fragment.
NOT_EXERCISED = ("dynamic-layers/*", "conf/fragments/*")

# Opens a task: `do_x:append:mx8() {`, `python do_y () {`.
FUNC_RE = re.compile(r"^(?:(?:python|fakeroot)\s+)*[\w${}.:+-]*\s*\(\s*\)\s*\{")
DEF_RE = re.compile(r"^def\s")
INCLUDE_RE = re.compile(r"^\s*(?:require|include|include_all)\s+(\S+)")
COMPAT_ASSIGN_RE = re.compile(
    r"""^\s*(COMPATIBLE_MACHINE|COMPATIBLE_HOST)((?::[\w${}.+-]+)*)\s*(?:\?\?=|\?=|:=|=)\s*["']([^"']*)["']""")


@lru_cache(maxsize=None)
def git(*args, check=True):
    p = subprocess.run(["git", "-C", LAYER] + list(args), capture_output=True, text=True)
    if check and p.returncode != 0:
        sys.exit(f"affected-machines: git {' '.join(args)}: {p.stderr.strip()}")
    return p.stdout


@lru_cache(maxsize=None)
def show(rev, path):
    """A file's content at a revision, or None when it does not exist there."""
    p = subprocess.run(["git", "-C", LAYER, "show", f"{rev}:{path}"],
                       capture_output=True, text=True)
    return p.stdout if p.returncode == 0 else None


def matches(path, patterns):
    return any(fnmatch.fnmatch(path, p) or fnmatch.fnmatch(os.path.basename(path), p)
               for p in patterns)


def cmd_table(args):
    """Print each machine's TUNE_PKGARCH and MACHINEOVERRIDES, from `bitbake -e`.
    MACHINE goes in a pre-read conf (-r), so local.conf must use `MACHINE ??=`."""
    machines = sorted(f[:-5] for f in os.listdir(os.path.join(LAYER, "conf", "machine"))
                      if f.endswith(".conf"))
    print("# machine\tTUNE_PKGARCH\tMACHINEOVERRIDES")
    with tempfile.TemporaryDirectory() as tmp:
        pre = os.path.join(tmp, "machine.conf")
        for m in machines:
            with open(pre, "w") as f:
                f.write(f'MACHINE = "{m}"\n')
            p = subprocess.run(["bitbake", "-r", pre, "-e"], capture_output=True, text=True)
            env = dict(re.findall(r'^(MACHINE|MACHINEOVERRIDES|TUNE_PKGARCH)="(.*)"$',
                                  p.stdout, re.M))
            if p.returncode != 0 or env.get("MACHINE") != m:
                sys.exit(f"affected-machines: bitbake -e for {m} failed or ignored MACHINE "
                         f"(a hard MACHINE assignment in local.conf?):\n{p.stderr[-2000:]}")
            print(f"{m}\t{env.get('TUNE_PKGARCH', '')}\t{env.get('MACHINEOVERRIDES', '')}",
                  flush=True)
            print(f"affected-machines: {m}", file=sys.stderr)
    return 0


def load_table(path):
    table = OrderedDict()
    with open(path) as f:
        for line in f:
            if not line.strip() or line.startswith("#"):
                continue
            m, tune, overrides = (line.rstrip("\n").split("\t") + ["", ""])[:3]
            table[m] = {"tune": tune, "overrides": [o for o in overrides.split(":") if o]}
    if not table:
        sys.exit(f"affected-machines: {path} lists no machines")
    return table


def statement_heads(text):
    """Map each line to the line opening its statement (assignment or task
    header); None inside a python def, which has no overrides to read."""
    heads = []
    func = cont = None
    in_def = False
    for line in text.split("\n"):
        if func is not None:
            heads.append(func)
            if line.rstrip() == "}":
                func = None
            continue
        if in_def:
            if line and not line[0].isspace():
                in_def = False
            else:
                heads.append(None)
                continue
        if cont is not None:
            heads.append(cont)
            cont = cont if line.rstrip().endswith("\\") else None
            continue
        heads.append(line)
        if FUNC_RE.match(line):
            func = None if line.rstrip().endswith("}") else line
        elif DEF_RE.match(line):
            in_def = True
            heads[-1] = None
        elif line.rstrip().endswith("\\"):
            cont = line
    return heads


def head_overrides(head):
    """The overrides in a statement header's variable or task name."""
    s = re.sub(r"^\s*(?:export\s+|python\s+|fakeroot\s+)*", "", head)
    name = re.split(r"[\s=?(+.{]", s, maxsplit=1)[0]
    return name.split(":")[1:]


class Selection:
    def __init__(self, table):
        self.table = table
        self.known = set(table).union(*(t["overrides"] for t in table.values()))
        self.direct = OrderedDict()    # machine -> {reasons}: its conf changed
        self.reached = OrderedDict()   # machine -> [reasons]
        self.narrowest = {}            # machine -> size of the narrowest reach
        self.global_reasons = []       # lines that can reach any machine
        self.nowhere = []
        self.untested = []
        self.ignored = []

    def machines(self, overrides):
        """The machines any of `overrides` applies to."""
        return {m for m, t in self.table.items()
                if any(o == m or o in t["overrides"] for o in overrides)}

    def reach(self, machines, reason):
        """Record a reach: None is any machine, an empty set none."""
        if machines is None:
            self.global_reasons.append(reason)
        elif not machines:
            self.nowhere.append(reason)
        for m in sorted(machines or ()):
            self.reached.setdefault(m, []).append(reason)
            self.narrowest[m] = min(self.narrowest.get(m, len(self.table)), len(machines))


def own_scope(text, table):
    """Machines a file allows by COMPATIBLE_MACHINE/HOST, overrides included
    (e.g. COMPATIBLE_HOST = '(null)' with COMPATIBLE_HOST:use-nxp-bsp = '.*').
    None when unrestricted or computed."""
    if text is None:
        return None
    known = set(table).union(*(t["overrides"] for t in table.values()))
    scope = None
    for var in ("COMPATIBLE_MACHINE", "COMPATIBLE_HOST"):
        plain, per = None, []
        for line in text.split("\n"):
            m = COMPAT_ASSIGN_RE.match(line)
            if not m or m.group(1) != var:
                continue
            toks = [t for t in m.group(2).split(":") if t]
            if "${" in m.group(3) or any(t in ("append", "prepend", "remove") for t in toks):
                return None
            if not toks:
                plain = m.group(3)
            elif all(t in known for t in toks):
                per.append((toks, m.group(3)))
        if plain is None and not per:
            continue
        allowed = set(table) if plain is None else allows(var, plain, table)
        if allowed is None:
            return None
        for toks, val in per:
            hit = {m for m in table if all(t in table[m]["overrides"] or t == m for t in toks)}
            a = allows(var, val, table)
            if a is None:
                return None
            allowed = (allowed - hit) | (hit & a)
        scope = allowed if scope is None else scope & allowed
    return None if scope == set(table) else scope


def allows(var, value, table):
    if var == "COMPATIBLE_HOST":
        # Matched against TARGET_SYS, not in the table: all-or-nothing only.
        return set() if value.strip() in ("(null)", "null", "^$", "(^$)") else set(table)
    try:
        regex = re.compile(value)
    except re.error:
        return None
    # BitBake matches COMPATIBLE_MACHINE against each MACHINEOVERRIDES entry.
    return {m for m, t in table.items() if any(regex.match(o) for o in t["overrides"] + [m])}


def recipe_scope(rev, path, table, seen=frozenset()):
    """Machines a recipe can be parsed for; an .inc takes its requirers' union.
    None when unrestricted."""
    own = own_scope(show(rev, path), table)
    if own is not None or not path.endswith(".inc"):
        return own
    name = re.escape(os.path.basename(path))
    users = [f.split(":", 1)[1] for f in git(
        "grep", "-l", "-E", rf"^\s*(require|include)\s+(\S*/)?{name}\s*$", rev,
        "--", *RECIPES, check=False).split("\n") if ":" in f]
    users = [u for u in users if u not in seen and not matches(u, NOT_EXERCISED)]
    scopes = [recipe_scope(rev, u, table, seen | {path}) for u in users]
    if not scopes or None in scopes:
        return None
    return set().union(*scopes)


def include_graph(rev, table):
    """{included file: machines including it, directly or not}."""
    def includes(path, seen):
        text = show(rev, path)
        if text is None or path in seen:
            return set()
        seen.add(path)
        found = set()
        for line in text.split("\n"):
            m = INCLUDE_RE.match(line)
            if not m or "${" in m.group(1):
                continue
            for cand in (os.path.normpath(os.path.join(os.path.dirname(path), m.group(1))),
                         os.path.normpath(m.group(1))):
                if show(rev, cand) is not None:
                    found.add(cand)
                    found |= includes(cand, seen)
                    break
        return found

    graph = {}
    for m in table:
        for inc in includes(f"conf/machine/{m}.conf", set()):
            graph.setdefault(inc, set()).add(m)
    return graph


def changed_lines(base, head):
    """{(old path, new path): (removed lines, added lines)}; None for an absent side."""
    out = OrderedDict()
    cur = None

    def flush():
        if cur is not None:
            out[(cur["old"], cur["new"])] = (cur["removed"], cur["added"])

    for line in git("diff", "-U0", "--find-renames", "--no-color", f"{base}...{head}").split("\n"):
        if line.startswith("diff --git "):
            flush()
            m = re.match(r"diff --git a/(.*) b/(.*)$", line)
            cur = {"old": m.group(1), "new": m.group(2), "removed": set(), "added": set()}
        elif cur is None:
            continue
        elif line.startswith("new file mode"):
            cur["old"] = None
        elif line.startswith("deleted file mode"):
            cur["new"] = None
        elif line.startswith("rename from "):
            cur["old"] = line[len("rename from "):]
        elif line.startswith("rename to "):
            cur["new"] = line[len("rename to "):]
        elif line.startswith("@@"):
            m = re.match(r"@@ -(\d+)(?:,(\d+))? \+(\d+)(?:,(\d+))? @@", line)
            a, b = int(m.group(1)), int(m.group(2) or 1)
            c, d = int(m.group(3)), int(m.group(4) or 1)
            cur["removed"].update(range(a, a + b))
            cur["added"].update(range(c, c + d))
    flush()
    return out


def judge(sel, rev, path, numbers, scope, label="COMPATIBLE_MACHINE"):
    """Reach, for each changed line, the machines its statement's overrides
    apply to, within `scope` (None: unrestricted). An unconditional line in an
    unrestricted file can reach any machine."""
    text = show(rev, path)
    if text is None:
        return
    lines, heads = text.split("\n"), statement_heads(text)
    for n in sorted(numbers):
        line = lines[n - 1].strip() if n <= len(lines) else ""
        if not line or line.startswith("#"):
            continue
        head = heads[n - 1]
        ovr = sorted({o for o in head_overrides(head) if o in sel.known}) if head else []
        hit = sel.machines(ovr) if ovr else None
        if scope is not None:
            hit = scope if hit is None else hit & scope
        why = ":" + ":".join(ovr) if ovr else label if scope is not None else "unconditional"
        sel.reach(hit, f"{path}:{n} ({why})")


def judge_file(sel, rev, path):
    """A file a recipe uses (patch, config): an override-named directory says
    which machines, else the recipe lines naming it, else its recipes' scope."""
    dirs = [c for c in path.split("/")[:-1] if c in sel.known]
    if dirs:
        sel.reach(sel.machines(dirs), f"{path} (in {'/'.join(dirs)}/)")
        return
    parts = path.split("/")
    if not parts[0].startswith("recipes-") or len(parts) < 3:
        sel.reach(None, f"{path}: outside a recipe directory")
        return
    where = "/".join(parts[:2])
    recipes = [f for f in git("ls-tree", "-r", "--name-only", rev, "--", where).split("\n")
               if matches(f, RECIPES)]
    name = os.path.basename(path)
    names = (name, name[:-3]) if name.endswith(".in") else (name,)
    named = False
    for r in recipes:
        numbers = {n for n, line in enumerate(show(rev, r).split("\n"), 1)
                   if any(x in line for x in names)}
        if numbers:
            named = True
            judge(sel, rev, r, numbers, recipe_scope(rev, r, sel.table))
    if not named:
        scopes = [recipe_scope(rev, r, sel.table) for r in recipes]
        sel.reach(None if not scopes or None in scopes else set().union(*scopes),
                  f"{path} (beside the recipes in {where})")


def judge_path(sel, rev, path, numbers, graph, head):
    if matches(path, NOT_EXERCISED):
        sel.untested.append(path)
    elif matches(path, NOT_PARSED):
        sel.ignored.append(path)
    elif re.match(r"conf/machine/[^/]+\.conf$", path):
        m = os.path.basename(path)[:-5]
        if m in sel.table:
            sel.direct.setdefault(m, set()).add(f"{path} changed")
        elif show(head, path) is None:
            sel.ignored.append(f"{path} (machine removed)")
        else:
            sel.reach(None, f"{path}: a machine missing from the table (regenerate it)")
    elif path.startswith("conf/machine/include/"):
        # An include no machine conf names may be required from anywhere.
        inc = graph.get(path)
        judge(sel, rev, path, numbers, inc, f"included by {len(inc)} machine(s)" if inc else "")
    elif path.startswith(("classes", "conf/", "lib/")) or path.endswith(".bbclass"):
        sel.reach(None, f"{path}: global configuration or class")
    elif matches(path, RECIPES):
        judge(sel, rev, path, numbers, recipe_scope(rev, path, sel.table))
    else:
        judge_file(sel, rev, path)


def collect(sel, base, head):
    mb = git("merge-base", base, head).strip()
    graphs = {head: include_graph(head, sel.table), mb: include_graph(mb, sel.table)}
    for (old, new), (removed, added) in changed_lines(base, head).items():
        for rev, path, numbers in ((head, new, added), (mb, old, removed)):
            if path is None:
                continue
            # A rename changes every line: a recipe's version, or whether a
            # dynamic layer holds it.
            if old and new and old != new:
                numbers = range(1, (show(rev, path) or "").count("\n") + 2)
            judge_path(sel, rev, path, numbers, graphs[rev], head)
    for attr in ("global_reasons", "nowhere", "untested", "ignored"):
        setattr(sel, attr, list(OrderedDict.fromkeys(getattr(sel, attr))))


def choose(sel, limit):
    """Order machines strongest evidence first, then cap to `limit`."""
    table = sel.table
    picked = OrderedDict()

    def add(m, role, why):
        picked.setdefault(m, (role, why))

    by_tune = OrderedDict()
    for m in table:
        by_tune.setdefault(table[m]["tune"], []).append(m)

    for m, why in sel.direct.items():
        add(m, "changed", "; ".join(sorted(why)))

    # Narrower changes rank first, so --max drops the widest: :mx8mp-nxp-bsp
    # before :imx.
    reached = set(sel.reached) | set(sel.direct)
    narrow = lambda m: sel.narrowest.get(m, 0)
    tunes = OrderedDict()
    for m in sorted(reached, key=lambda m: (narrow(m), m)):
        tunes.setdefault(table[m]["tune"], []).append(m)
    for tune, ms in sorted(tunes.items(), key=lambda kv: (narrow(kv[1][0]), -len(kv[1]))):
        rep = next((m for m in ms if m in picked), ms[0])
        files = sorted({r.split(":")[0].split(" ")[0] for r in sel.reached.get(rep, [])})
        add(rep, "reached", f"{tune} ({len(ms)} reached): {', '.join(files)}")
        # test_machine_signatures compares machines of one tune: prefer one
        # the change does not reach.
        others = [m for m in by_tune[tune] if m not in picked]
        peer = next((m for m in others if m not in reached), others[0] if others else None)
        if peer:
            add(peer, "peer", f"{tune}: compared with {rep}")

    rest = sorted((t for t in by_tune if t not in tunes), key=lambda t: (-len(by_tune[t]), t))
    if sel.global_reasons:
        # One machine per tune, then the second of each, so --max keeps the
        # most tunes.
        why = f"{len(sel.global_reasons)} line(s) can reach any machine"
        for tune in rest:
            add(by_tune[tune][0], "any", f"{tune}: {why}")
        for tune in rest:
            if len(by_tune[tune]) > 1:
                add(by_tune[tune][1], "peer", f"{tune}: compared with {by_tune[tune][0]}")
    elif picked and rest:
        add(by_tune[rest[0]][0], "control", f"{rest[0]}, another tune: allarch and native recipes")

    if len(picked) == 1:
        m = next(iter(picked))
        other = [x for x in by_tune[table[m]["tune"]] if x != m] or [x for x in table if x != m]
        if other:
            add(other[0], "peer", "test_machine_signatures needs a second machine")

    full = len(picked)
    if limit and len(picked) > limit:
        picked = OrderedDict(list(picked.items())[:limit])
    return picked, full


def shards(picked, table, size):
    """Split the picked machines into runs of at most `size` machines that
    together compare what one run over all of them would.

    test_machine_signatures compares only within a run, so each run keeps a
    machine of its tune (the tune anchor) and the anchor (the control, if
    any), for machine-tuned and for allarch/native tasks."""
    if not picked:
        return []
    anchor = next((m for m, (r, _) in picked.items() if r == "control"), next(iter(picked)))
    by_tune = OrderedDict()
    for m in picked:
        if m != anchor:
            by_tune.setdefault(table[m]["tune"], []).append(m)
    out = []
    for tune, ms in by_tune.items():
        # The anchor stands in for the tune anchor on its own tune.
        if table[anchor]["tune"] == tune:
            head, tail = [], [anchor]
        else:
            head, tail, ms = ms[:1], [anchor], ms[1:]
        step = max(1, size - len(head) - len(tail))
        chunks = [ms[i:i + step] for i in range(0, len(ms), step)] or [[]]
        for n, chunk in enumerate(chunks, 1):
            name = tune if len(chunks) == 1 else f"{tune}-{n}"
            out.append({"name": name, "machines": " ".join(head + chunk + tail)})
    return out or [{"name": table[anchor]["tune"], "machines": anchor}]


def cmd_select(args):
    table = load_table(args.table)
    sel = Selection(table)
    if args.all:
        picked = OrderedDict((m, ("all", "full run")) for m in table)
        full = len(picked)
    else:
        if not args.base:
            sys.exit("affected-machines: select needs --base or --all")
        collect(sel, args.base, args.head)
        picked, full = choose(sel, args.max)

    if args.format == "shards":
        print(json.dumps(shards(picked, table, args.shard_size)))
    elif args.format == "json":
        print(json.dumps({
            "machines": list(picked),
            "roles": {m: {"role": r, "reason": w} for m, (r, w) in picked.items()},
            "reached": sorted(set(sel.reached) | set(sel.direct)),
            "global": sel.global_reasons,
            "nowhere": sel.nowhere,
            "capped": full > len(picked),
            "untested": sel.untested,
            "ignored": sel.ignored,
            "machines_available": len(table),
        }, indent=2))
    else:
        print(" ".join(picked))
    if args.explain:
        e = sys.stderr
        print(f"selected {len(picked)}/{len(table)} machines"
              + (f" (capped from {full} by --max)" if full > len(picked) else ""), file=e)
        if sel.global_reasons:
            print(f"{len(sel.global_reasons)} changed line(s) can reach any machine:", file=e)
            for r in sel.global_reasons[:args.reasons]:
                print(f"    {r}", file=e)
            if len(sel.global_reasons) > args.reasons:
                print(f"    ... {len(sel.global_reasons) - args.reasons} more", file=e)
        for m, (role, why) in picked.items():
            print(f"  {m:28} {role:8} {why}", file=e)
        for r in sel.nowhere[:args.reasons]:
            print(f"  reaches no machine: {r}", file=e)
        for p in sel.untested:
            print(f"  not exercised: {p}", file=e)
        if not picked and not sel.untested:
            print("  no machine needed: nothing BitBake parses changed", file=e)
    return 0


def main(argv=None):
    global LAYER
    ap = argparse.ArgumentParser(description=__doc__.split("\n\n")[0])
    ap.add_argument("--layer", default=LAYER, help="the layer (default: this script's)")
    sub = ap.add_subparsers(dest="cmd", required=True)
    sub.add_parser("table", help="print the machine table (needs a BitBake environment)")
    s = sub.add_parser("select", help="print the machines a change needs")
    s.add_argument("--table", required=True, help="the output of `table`")
    s.add_argument("--base", help="the revision the change is based on")
    s.add_argument("--all", action="store_true", help="every machine in the table (the full run)")
    s.add_argument("--head", default="HEAD")
    s.add_argument("--max", type=int, default=0, help="keep at most this many machines")
    s.add_argument("--format", choices=("list", "json", "shards"), default="list",
                   help="shards: a JSON list of runs, {name, machines}, for a CI matrix")
    s.add_argument("--shard-size", type=int, default=4,
                   help="with --format shards: at most this many machines per run")
    s.add_argument("--explain", action="store_true", help="say why, on stderr")
    s.add_argument("--reasons", type=int, default=5,
                   help="with --explain: list this many lines that can reach any machine")
    args = ap.parse_args(argv)
    LAYER = os.path.abspath(args.layer)
    return {"table": cmd_table, "select": cmd_select}[args.cmd](args)


if __name__ == "__main__":
    sys.exit(main())
