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

METADATA = ("*.bb", "*.bbappend", "*.bbclass", "*.inc", "*.conf")

# Never parsed by BitBake: no machine needed.
NOT_PARSED = (".github/*", "contrib/*", "scripts/*", "*.md", "README*",
              "LICENSE*", "COPYING*", "SECURITY*", ".oelint*", "oelint.*",
              "wic/*", ".git*", ".editorconfig", "default-registry/*")

# Parsed only when a build enables them; check-layer does not.
NOT_ENABLED = ("conf/fragments/*",)

# An unconditional line is the same on every machine unless it reads one of
# these, or changes dependencies a machine may not provide.
MACHINE_DEPENDENT = re.compile(
    r"\b(?:MACHINE\w*|SOC_\w+|TUNE_\w+|DEFAULTTUNE|TARGET_ARCH|TARGET_SYS|HOST_ARCH|"
    r"HOST_SYS|PACKAGE_ARCH\w*|BUILD_64BIT_KERNEL|KERNEL_\w+|UBOOT_\w+|IMAGE_\w+|"
    r"LAYERSERIES_\w+|BBFILE_COLLECTIONS|OVERRIDES)\b")
# Inline python reading only these is the same on every machine of a run.
UNIFORM_VARS = {"DISTRO_FEATURES", "PACKAGECONFIG", "PN", "BPN", "PV", "PR", "BP", "P",
                "LICENSE_FLAGS_ACCEPTED", "PTEST_ENABLED", "SRCPV", "BB_CURRENTTASK",
                "BBPATH", "THISDIR", "FILE_DIRNAME", "WORKDIR", "S", "B", "D", "UNPACKDIR"}
INLINE_RE = re.compile(r"\$\{@(.*)")
QUOTED_VAR = re.compile(r"""['"]([A-Z][A-Z0-9_]*)['"]""")
DEPENDENCY = re.compile(
    r"^\s*(?:export\s+)?(?:DEPENDS|RDEPENDS|RRECOMMENDS|RSUGGESTS|RPROVIDES|PROVIDES|"
    r"RCONFLICTS|RREPLACES|PACKAGES|PACKAGES_DYNAMIC|COMPATIBLE_\w+|PREFERRED_\w+|"
    r"BBCLASSEXTEND|SKIP_RECIPE\w*|EXCLUDE_FROM_WORLD|REQUIRED_\w+|ANY_OF_\w+|"
    r"CONFLICT_\w+|PACKAGE_ARCH|MULTILIBS?)\b"
    r"|^\s*(?:inherit|inherit_defer|require|include|include_all|addtask|deltask)\b"
    r"|\[(?:depends|rdepends|recrdeptask|recideptask|deptask|rdeptask|noexec)\]")
FETCH = re.compile(r"^\s*(?:SRC_URI|FILESEXTRAPATHS|FILESPATH|FILESOVERRIDES)\b")

# Opens a task: `do_x:append:mx8() {`, `python do_y () {`.
FUNC_RE = re.compile(r"^(?:(?:python|fakeroot)\s+)*[\w${}.:+-]*\s*\(\s*\)\s*\{")
DEF_RE = re.compile(r"^def\s")
INCLUDE_RE = re.compile(r"^\s*(?:require|include|include_all)\s+(\S+)")
COMPAT_RE = re.compile(r'^\s*COMPATIBLE_MACHINE\s*(?:\?\?=|\?=|:=|=)\s*"([^"]*)"')
COMPAT_ASSIGN_RE = re.compile(
    r"""^\s*(COMPATIBLE_MACHINE|COMPATIBLE_HOST)((?::[\w${}.+-]+)*)\s*(?:\?\?=|\?=|:=|=)\s*["']([^"']*)["']""")
WORD_RE = re.compile(r"[A-Za-z0-9_-]+")


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
    """Map each line to the line opening its statement (assignment or task header)."""
    heads, lines = [], text.split("\n")
    func = cont = None
    in_def = False
    for line in lines:
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


def own_scope(text, table):
    """Machines a file allows by COMPATIBLE_MACHINE/HOST, overrides included
    (e.g. COMPATIBLE_HOST = '(null)' with COMPATIBLE_HOST:use-nxp-bsp = '.*').
    None when unrestricted or computed."""
    if text is None:
        return None
    known = set(table)
    for t in table.values():
        known.update(t["overrides"])
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
        return compatible(table, re.compile(value))
    except re.error:
        return None


def compatible(table, regex):
    """BitBake matches COMPATIBLE_MACHINE against each MACHINEOVERRIDES entry."""
    return {m for m, t in table.items()
            if any(regex.match(o) for o in t["overrides"] + [m])}



class Selection:
    def __init__(self, table):
        self.table = table
        self.reached = OrderedDict()   # machine -> [reasons]
        self.narrowest = {}            # machine -> size of the narrowest reason
        self.direct = OrderedDict()    # machine -> [reasons]
        self.global_reasons = []
        self.uniform_reasons = []
        self.nowhere = []
        self.untested = []
        self.ignored = []

    def reach(self, machines, reason):
        for m in sorted(machines):
            self.reached.setdefault(m, []).append(reason)
            self.narrowest[m] = min(self.narrowest.get(m, len(self.table)), len(machines))

    def everywhere(self, reason):
        self.global_reasons.append(reason)


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


def recipe_scope(rev, path, table, seen=None):
    """Machines a recipe can be parsed for; an .inc takes its requirers' union.
    None when unrestricted."""
    own = own_scope(show(rev, path), table)
    if own is not None:
        return own
    if not path.endswith(".inc"):
        return None
    seen = (seen or set()) | {path}
    name = re.escape(os.path.basename(path))
    users = [f.split(":", 1)[1] for f in git(
        "grep", "-l", "-E", rf"^\s*(require|include)\s+(\S*/)?{name}\s*$", rev,
        "--", "*.bb", "*.bbappend", "*.inc", check=False).split("\n") if ":" in f]
    users = [u for u in users if u not in seen and not u.startswith("dynamic-layers/")]
    if not users:
        return None
    scopes = []
    for u in users:
        sc = recipe_scope(rev, u, table, seen)
        if sc is None:
            return None
        scopes.append(sc)
    return set().union(*scopes)


def class_scope(rev, path, table):
    """Union of the scopes of the class's inheriters; None if any is unrestricted."""
    name = os.path.basename(path)[:-len(".bbclass")]
    hits = git("grep", "-n", "-E", r"^\s*(inherit|inherit_defer|INHERIT\s*\+?=)", rev,
               "--", "*.bb", "*.bbappend", "*.inc", "*.bbclass", "*.conf",
               check=False).split("\n")
    users = set()
    for h in hits:
        parts = h.split(":", 3)
        if len(parts) < 4:
            continue
        f, text = parts[1], parts[3]
        if f.startswith("dynamic-layers/"):
            continue
        if "${" in text:
            return None
        if re.search(rf"(^|[\s\"]){re.escape(name)}($|[\s\"])", text):
            if f.endswith(".conf"):
                return None
            users.add(f)
    users.discard(path)
    if not users:
        return None
    scopes = []
    for u in sorted(users):
        sc = class_scope(rev, u, table) if u.endswith(".bbclass") else recipe_scope(rev, u, table)
        if sc is None:
            return None
        scopes.append(sc)
    return set().union(*scopes)


def machine_dependent(sel, rev, path, head, line):
    """Why an unconditional line can still differ between machines, or ''."""
    m = MACHINE_DEPENDENT.search(head) or MACHINE_DEPENDENT.search(line)
    if m:
        return f"reads {m.group(0)} or similar"
    for text in (head, line):
        py = INLINE_RE.search(text)
        if py:
            unknown = sorted(set(QUOTED_VAR.findall(py.group(1))) - UNIFORM_VARS)
            if unknown or "d.getVar(" in py.group(1) and not QUOTED_VAR.search(py.group(1)):
                return f"inline python reads {', '.join(unknown) or 'a computed variable'}"
    if DEPENDENCY.search(head) or DEPENDENCY.search(line):
        return "changes dependencies, which a machine may not provide"
    if FETCH.search(head) and override_dirs(rev, os.path.dirname(path), sel):
        return "fetches files that per-machine directories can replace"
    return ""


def override_dirs(rev, d, sel):
    """Override names used as directories under `d` (FILESOVERRIDES)."""
    known = known_overrides(sel)
    found = set()
    for f in git("ls-tree", "-r", "--name-only", rev, "--", d or ".", check=False).split("\n"):
        found.update(c for c in f.split("/")[:-1] if c in known)
    return found


def known_overrides(sel):
    known = set(sel.table)
    for t in sel.table.values():
        known.update(t["overrides"])
    return known


def judge_lines(sel, rev, path, numbers, scope, what, scope_label="COMPATIBLE_MACHINE"):
    """Record the machines each changed line can affect, within `scope`."""
    text = show(rev, path)
    if text is None or not numbers:
        return
    lines, heads = text.split("\n"), statement_heads(text)
    known = set()
    for t in sel.table.values():
        known.update(t["overrides"])
    known.update(sel.table)
    for n in sorted(numbers):
        i = n - 1
        if i >= len(lines):
            continue
        line = lines[i].strip()
        if not line or line.startswith("#"):
            continue
        head = heads[i]
        if head is None:
            sel.everywhere(f"{path}:{n} ({what}): python code, conditions unknown")
            continue
        ovr = [o for o in head_overrides(head) if o in known]
        m = COMPAT_RE.match(head)
        if m:
            ovr += [w for w in WORD_RE.findall(m.group(1)) if w in known]
        if not ovr:
            if scope is None:
                why = machine_dependent(sel, rev, path, head, lines[i])
                if why:
                    sel.everywhere(f"{path}:{n} ({what}): {why}")
                else:
                    sel.uniform_reasons.append(f"{path}:{n} ({what})")
            elif scope:
                sel.reach(scope, f"{path} ({what}, {scope_label})")
            else:
                sel.nowhere.append(f"{path}:{n} ({what}): {scope_label} allows no machine in the table")
            continue
        hit = {mm for mm, t in sel.table.items()
               if any(o in t["overrides"] or o == mm for o in ovr)}
        if scope is not None:
            hit &= scope
        if hit:
            sel.reach(hit, f"{path} (:{':'.join(sorted(set(ovr)))})")
        else:
            sel.nowhere.append(f"{path}:{n} ({what}): :{':'.join(sorted(set(ovr)))} applies to no machine in the table")


def collect(sel, base, head):
    mb = git("merge-base", base, head).strip()
    graph_new = include_graph(head, sel.table)
    graph_old = include_graph(mb, sel.table)
    for (old, new), (removed, added) in changed_lines(base, head).items():
        # dynamic-layers/ is not parsed here: moving a file across is an add or remove.
        dyn = lambda p: p is not None and p.startswith("dynamic-layers/")
        if dyn(new) and old is not None and not dyn(old):
            sel.untested.append(new)
            new, added = None, set()
            removed = set(range(1, (show(mb, old) or "").count("\n") + 2))
        elif dyn(old) and new is not None and not dyn(new):
            old, removed = None, set()
            added = set(range(1, (show(head, new) or "").count("\n") + 2))
        path = new or old
        if dyn(path):
            sel.untested.append(path)
            continue
        if matches(path, NOT_ENABLED):
            sel.untested.append(path)
            continue
        if matches(path, NOT_PARSED):
            sel.ignored.append(path)
            continue
        m = re.match(r"conf/machine/([^/]+)\.conf$", path)
        if m:
            if m.group(1) in sel.table:
                sel.direct.setdefault(m.group(1), []).append(f"{path} changed")
            elif new is None:
                sel.ignored.append(f"{path} (machine removed)")
            else:
                sel.everywhere(f"{path}: a machine missing from the table (regenerate it)")
            continue
        if path.startswith("conf/machine/include/"):
            includers = graph_new.get(new, set()) | graph_old.get(old, set())
            if not includers:
                sel.ignored.append(f"{path} (no machine includes it)")
                continue
            if not (added or removed):
                sel.reach(includers, f"{path} (included)")
            label = f"included by {len(includers)} machine(s)"
            if new:
                judge_lines(sel, head, new, added, includers, "added", label)
            if old:
                judge_lines(sel, mb, old, removed, includers, "removed", label)
            continue
        if path == "conf/layer.conf" or path.startswith("classes/") or path.startswith("lib/"):
            if path.endswith(".bbclass") and (added or removed):
                if new:
                    judge_lines(sel, head, new, added, class_scope(head, new, sel.table),
                                "added", "inherited by restricted recipes")
                if old:
                    judge_lines(sel, mb, old, removed, class_scope(mb, old, sel.table),
                                "removed", "inherited by restricted recipes")
            else:
                sel.everywhere(f"{path}: global configuration")
            continue
        if matches(path, METADATA):
            if not (added or removed):
                scope = recipe_scope(head, new, sel.table) if new else None
                if scope is None:
                    sel.everywhere(f"{path}: renamed, and the recipe is not restricted")
                else:
                    sel.reach(scope, f"{path} (renamed, COMPATIBLE_MACHINE)")
                continue
            if new:
                judge_lines(sel, head, new, added, recipe_scope(head, new, sel.table), "added")
            if old:
                judge_lines(sel, mb, old, removed, recipe_scope(mb, old, sel.table), "removed")
            continue
        # Other files: an override-named directory (FILESOVERRIDES) says
        # which machines, else the statements naming the file do.
        dirs = [c for c in path.split("/")[:-1] if c in known_overrides(sel)]
        if dirs:
            sel.reach({m for m, t in sel.table.items()
                       if any(d in t["overrides"] or d == m for d in dirs)},
                      f"{path} (in {'/'.join(dirs)}/)")
            continue
        refs = referencing_lines(head, path) or referencing_lines(mb, path)
        if not refs:
            sel.everywhere(f"{path}: not referenced by name, assumed to reach every machine")
        for rev, f, numbers in refs:
            what = f"uses {os.path.basename(path)}"
            mm = re.match(r"conf/machine/([^/]+)\.conf$", f)
            if mm:
                if mm.group(1) in sel.table:
                    sel.reach({mm.group(1)}, f"{f} ({what})")
            elif f.startswith("conf/machine/include/"):
                graph = graph_new if rev == head else graph_old
                inc = graph.get(f, set())
                judge_lines(sel, rev, f, numbers, inc, what, f"included by {len(inc)} machine(s)")
            elif f.endswith(".bbclass"):
                judge_lines(sel, rev, f, numbers, class_scope(rev, f, sel.table), what,
                            "inherited by restricted recipes")
            else:
                judge_lines(sel, rev, f, numbers, recipe_scope(rev, f, sel.table), what)


def referencing_lines(rev, path):
    """[(rev, file, line numbers)] naming `path`, in its recipe dir or the layer."""
    name = os.path.basename(path)
    names = {name, name[:-3]} if name.endswith(".in") else {name}
    parts = path.split("/")
    if parts[0].startswith("recipes-") and len(parts) >= 3:
        where = ["--", "/".join(parts[:2])]
    else:
        where = ["--", "*.bb", "*.bbappend", "*.inc", "*.bbclass", "*.conf"]
    out = {}
    for n in names:
        for h in git("grep", "-n", "-F", n, rev, *where, check=False).split("\n"):
            bits = h.split(":", 3)
            if len(bits) < 4 or not matches(bits[1], METADATA) or bits[1].startswith("dynamic-layers/"):
                continue
            out.setdefault(bits[1], set()).add(int(bits[2]))
    return [(rev, f, nums) for f, nums in sorted(out.items())]


def choose(sel, limit):
    """Order machines strongest evidence first, then cap to `limit`."""
    table = sel.table
    picked = OrderedDict()

    def add(m, role, why):
        if m not in picked:
            picked[m] = (role, why)

    by_tune = OrderedDict()
    for m in table:
        by_tune.setdefault(table[m]["tune"], []).append(m)

    for m, why in sel.direct.items():
        add(m, "changed", "; ".join(why))

    reached = set(sel.reached) | set(sel.direct)
    # Narrower changes rank first: :mx8mp-nxp-bsp before :imx.
    narrow = lambda m: sel.narrowest.get(m, 0 if m in sel.direct else len(table))
    tunes = OrderedDict()
    for m in sorted(reached, key=lambda m: (narrow(m), m)):
        tunes.setdefault(table[m]["tune"], []).append(m)
    tunes = OrderedDict(sorted(tunes.items(), key=lambda kv: (narrow(kv[1][0]), -len(kv[1]))))
    for tune, ms in tunes.items():
        rep = next((m for m in ms if m in picked), None)
        if rep is None:
            rep = ms[0]
            add(rep, "reached", f"{tune} ({len(ms)} reached): "
                + "; ".join(sorted(set(sel.reached.get(rep, sel.direct.get(rep, []))))))
        # test_machine_signatures compares machines within a tune: add an
        # unreached one, else the reached one least like the first.
        others = [m for m in by_tune[tune] if m not in picked]
        unreached = [m for m in others if m not in reached]
        if unreached:
            add(unreached[0], "peer", f"{tune}, not reached: compares against the change")
        elif others:
            ov = set(table[rep]["overrides"])
            far = max(others, key=lambda m: (len(ov ^ set(table[m]["overrides"])), m))
            add(far, "pair", f"{tune}, also reached, least like {rep}: compares the two")

    rest = sorted((t for t in by_tune if t not in tunes), key=lambda t: (-len(by_tune[t]), t))
    if sel.global_reasons:
        files = sorted({r.split(":")[0] for r in sel.global_reasons})
        why = f"{len(sel.global_reasons)} line(s) in {', '.join(files)} can reach any machine"
        for tune in rest:
            add(by_tune[tune][0], "tune", f"{tune}: {why}")
        for tune, ms in by_tune.items():
            more = [m for m in ms if m not in picked]
            if more and sum(1 for m in ms if m in picked) == 1:
                add(more[0], "peer", f"second {tune} machine, for signature comparison")
    else:
        if sel.uniform_reasons and not tunes:
            files = sorted({r.split(":")[0] for r in sel.uniform_reasons})
            big = max(by_tune, key=lambda t: (len(by_tune[t]), t))
            why = f"{len(sel.uniform_reasons)} line(s) in {', '.join(files)} change every machine alike"
            for m in by_tune[big][:2]:
                add(m, "uniform", f"{big}: {why}")
            tunes[big] = by_tune[big][:2]
            rest = [t for t in rest if t != big]
        if (reached or sel.uniform_reasons) and rest:
            m = by_tune[rest[0]][0]
            add(m, "control", f"{rest[0]}, another tune: allarch and native recipes")

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

    test_machine_signatures compares a task only between the machines of one
    run, keyed by the task's tune: a machine-tuned task between machines of
    that tune, an allarch or native task between every machine. So each run
    keeps one machine of its tune (the tune anchor) and one machine of the
    whole selection (the anchor, the control when there is one), and every
    other machine is compared with both."""
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
            "uniform": sel.uniform_reasons,
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
        reached = set(sel.reached) | set(sel.direct)
        print(f"selected {len(picked)}/{len(table)} machines"
              + (f" (capped from {full} by --max)" if full > len(picked) else ""), file=e)
        print(f"the change is known to reach {len(reached)} machine(s)", file=e)
        if sel.uniform_reasons:
            print(f"{len(sel.uniform_reasons)} changed line(s) change every machine alike", file=e)
        if sel.global_reasons:
            print(f"and {len(sel.global_reasons)} changed line(s) can reach any machine:", file=e)
            for r in sel.global_reasons[:args.reasons]:
                print(f"    {r}", file=e)
            if len(sel.global_reasons) > args.reasons:
                print(f"    ... {len(sel.global_reasons) - args.reasons} more", file=e)
        for m, (role, why) in picked.items():
            print(f"  {m:28} {role:8} {why}", file=e)
        for r in sel.nowhere[:args.reasons]:
            print(f"  reaches no machine: {r}", file=e)
        for p in sel.untested:
            print(f"  not exercised: {p} (dynamic layer, absent from this run)", file=e)
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
    return cmd_table(args) if args.cmd == "table" else cmd_select(args)


if __name__ == "__main__":
    sys.exit(main())
