#!/usr/bin/env python3
"""Tests for affected-machines.py: a synthetic layer in a temporary git
repository, a hand-written machine table, no BitBake.

    python3 -m unittest discover -s contrib/check-layer/tests
"""

import importlib.util
import json
import os
import subprocess
import sys
import tempfile
import textwrap
import unittest
from collections import OrderedDict

HERE = os.path.dirname(os.path.abspath(__file__))
SCRIPT = os.path.join(HERE, "..", "affected-machines.py")

spec = importlib.util.spec_from_file_location("affected_machines", SCRIPT)
am = importlib.util.module_from_spec(spec)
spec.loader.exec_module(am)

# Two SoCs on tune A (soca, socc), one on tune B, two PowerPC machines.
TABLE = """\
# machine\tTUNE_PKGARCH\tMACHINEOVERRIDES
a1\ttunea\tsoca:fam:a1
a2\ttunea\tsoca:fam:a2
b1\ttuneb\tsocb:fam:b1
c1\ttunea\tsocc:fam:c1
p1\ttunep\tppc:p1
p2\ttunep\tppc:p2
"""

LAYER = {
    "conf/layer.conf": 'BBPATH .= ":${LAYERDIR}"\n',
    "conf/machine/a1.conf": "require conf/machine/include/soca.inc\n",
    "conf/machine/a2.conf": "require include/soca.inc\n",
    "conf/machine/b1.conf": "require conf/machine/include/socb.inc\n",
    "conf/machine/c1.conf": 'MACHINEOVERRIDES =. "socc:fam:"\n',
    "conf/machine/p1.conf": "",
    "conf/machine/p2.conf": "",
    "conf/machine/include/soca.inc": 'MACHINEOVERRIDES =. "soca:fam:"\nX = "1"\n',
    "conf/machine/include/socb.inc": 'MACHINEOVERRIDES =. "socb:fam:"\n',
    "recipes-x/foo/foo.bb": textwrap.dedent("""\
        SUMMARY = "foo"
        LICENSE = "MIT"

        SRC_URI = "file://a.c"
        SRC_URI:append:soca = " file://fix.patch"

        EXTRA:soca = "\\
            one \\
        "

        do_install:append:socb() {
            install -d ${D}/b
        }
        """),
    "recipes-x/foo/files/fix.patch": "--- a\n+++ b\n",
    "recipes-x/foo/files/a.c": "int a;\n",
    "recipes-x/bar/bar.bb": textwrap.dedent("""\
        SUMMARY = "bar"
        COMPATIBLE_MACHINE = "(socb)"
        inherit k
        V = "1"
        """),
    "recipes-x/baz/baz.inc": 'W = "1"\n',
    "recipes-x/qux/qux.bb": 'COMPATIBLE_MACHINE = "(ppc)"\nrequire recipes-x/baz/baz.inc\n',
    "classes/k.bbclass": 'K = "1"\n',
    "recipes-x/hdr/hdr.bb": "COMPATIBLE_HOST = '(null)'\nCOMPATIBLE_HOST:ppc = '.*'\nH = \"1\"\n",
    "recipes-x/tee/tee.bb": 'COMPATIBLE_MACHINE ?= "invalid"\nCOMPATIBLE_MACHINE:socb = "socb"\nT = "1"\n',
    "recipes-x/none/none.bb": 'COMPATIBLE_MACHINE = "(nosuchsoc)"\nN = "1"\n',
    "README.md": "readme\n",
    "dynamic-layers/other/recipes-y/y.bbappend": 'Y = "1"\n',
}


class Layer:
    def __init__(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.path = self.tmp.name
        self.table = os.path.join(self.path, ".table.tsv")
        with open(self.table, "w") as f:
            f.write(TABLE)
        self.git("init", "-q", "-b", "master")
        self.git("config", "user.email", "t@example.com")
        self.git("config", "user.name", "t")
        for p, c in LAYER.items():
            self.write(p, c)
        self.git("add", "-A")
        self.git("commit", "-q", "-m", "base")
        self.git("tag", "base")

    def git(self, *args):
        return subprocess.run(["git", "-C", self.path] + list(args), check=True,
                              capture_output=True, text=True).stdout

    def write(self, path, content):
        full = os.path.join(self.path, path)
        os.makedirs(os.path.dirname(full), exist_ok=True)
        with open(full, "w") as f:
            f.write(content)

    def edit(self, path, old, new):
        full = os.path.join(self.path, path)
        with open(full) as f:
            text = f.read()
        assert old in text, (path, old)
        self.write(path, text.replace(old, new, 1))

    def commit(self):
        self.git("add", "-A")
        self.git("commit", "-q", "-m", "change")

    def select(self, *extra):
        p = subprocess.run([sys.executable, SCRIPT, "--layer", self.path, "select",
                            "--table", self.table, "--base", "base", "--format", "json"]
                           + list(extra), capture_output=True, text=True)
        if p.returncode != 0:
            raise AssertionError(p.stderr)
        return json.loads(p.stdout)


class StatementHeads(unittest.TestCase):
    def test_task_body_continuation_and_def(self):
        text = textwrap.dedent("""\
            A:x = "\\
                1 \\
            "
            do_y:append:z() {
                echo
            }
            def f(d):
                return 1
            B = "2"
            """)
        heads = am.statement_heads(text)
        self.assertEqual(heads[1], 'A:x = "\\')
        self.assertEqual(heads[2], 'A:x = "\\')
        self.assertEqual(heads[4], "do_y:append:z() {")
        self.assertEqual(heads[5], "do_y:append:z() {")
        self.assertIsNone(heads[7])
        self.assertEqual(heads[8], 'B = "2"')

    def test_head_overrides(self):
        self.assertEqual(am.head_overrides('SRC_URI:append:soca = "x"'), ["append", "soca"])
        self.assertEqual(am.head_overrides("python do_x:prepend:ppc () {"), ["prepend", "ppc"])
        self.assertEqual(am.head_overrides('export FOO = "1"'), [])


class Select(unittest.TestCase):
    def setUp(self):
        self.layer = Layer()

    def tearDown(self):
        self.layer.tmp.cleanup()

    def test_override_reaches_its_machines_and_adds_peer_and_control(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'file://fix.patch"', 'file://fix.patch file://more.patch"')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual(j["reached"], ["a1", "a2"])
        self.assertEqual(j["global"], [])
        # a1 for tune A, c1 as the unreached peer of tune A, p1 as the control
        self.assertEqual(j["machines"], ["a1", "c1", "p1"])
        self.assertEqual(j["roles"]["c1"]["role"], "peer")
        self.assertEqual(j["roles"]["p1"]["role"], "control")

    def test_task_body_inherits_header_override(self):
        self.layer.edit("recipes-x/foo/foo.bb", "install -d ${D}/b", "install -d ${D}/b2")
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual(j["reached"], ["b1"])

    def test_continuation_inherits_first_line_override(self):
        self.layer.edit("recipes-x/foo/foo.bb", "    one \\", "    two \\")
        self.layer.commit()
        self.assertEqual(self.layer.select()["reached"], ["a1", "a2"])

    def test_uniform_line_needs_a_pair_and_a_control(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'SRC_URI = "file://a.c"', 'SRC_URI = "file://b.c"')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["global"], j["reached"]), ([], []))
        self.assertTrue(j["uniform"])
        # two machines of the most common tune (A), one of another
        self.assertEqual(j["machines"], ["a1", "a2", "p1"])

    def test_dependency_line_reaches_any_machine(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'LICENSE = "MIT"\n', 'LICENSE = "MIT"\nDEPENDS += "zlib"\n')
        self.layer.commit()
        j = self.layer.select()
        self.assertTrue(j["global"])
        tunes = {"a1": "a", "a2": "a", "c1": "a", "b1": "b", "p1": "p", "p2": "p"}
        self.assertEqual({tunes[m] for m in j["machines"]}, {"a", "b", "p"})
        self.assertEqual(sum(1 for m in j["machines"] if tunes[m] == "a"), 2)

    def test_machine_variable_reaches_any_machine(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'LICENSE = "MIT"\n', 'LICENSE = "MIT"\nS = "${WORKDIR}/${MACHINE}"\n')
        self.layer.commit()
        self.assertTrue(self.layer.select()["global"])

    def test_fetch_with_per_machine_directories_reaches_any_machine(self):
        self.layer.write("recipes-x/foo/files/soca/a.c", "int b;\n")
        self.layer.commit()
        self.layer.git("tag", "-f", "base")
        self.layer.edit("recipes-x/foo/foo.bb", 'SRC_URI = "file://a.c"', 'SRC_URI = "file://a.c file://c.c"')
        self.layer.commit()
        self.assertTrue(self.layer.select()["global"])

    def test_file_in_override_directory_reaches_that_override(self):
        self.layer.write("recipes-x/foo/files/socb/a.c", "int b;\n")
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["reached"], j["global"]), (["b1"], []))

    def test_file_outside_recipes_follows_the_config_naming_it(self):
        self.layer.write("wic-files/board.wks.in", "part /boot\n")
        self.layer.edit("conf/machine/include/socb.inc", "\n", '\nWKS_FILE = "board.wks.in"\n')
        self.layer.commit()
        self.layer.git("tag", "-f", "base")
        self.layer.write("wic-files/board.wks.in", "part /boot --size 64\n")
        self.layer.commit()
        self.assertEqual(self.layer.select()["reached"], ["b1"])

    def test_inline_python_on_distro_features_is_uniform(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'LICENSE = "MIT"\n',
                        'LICENSE = "MIT"\nEXTRA = "${@bb.utils.contains(\'DISTRO_FEATURES\', \'x11\', \'a\', \'b\', d)}"\n')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual(j["global"], [])
        self.assertTrue(j["uniform"])

    def test_inline_python_on_machine_features_reaches_any_machine(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'LICENSE = "MIT"\n',
                        'LICENSE = "MIT"\nEXTRA = "${@bb.utils.contains(\'MACHINE_FEATURES\', \'x\', \'a\', \'b\', d)}"\n')
        self.layer.commit()
        self.assertTrue(self.layer.select()["global"])

    def test_renamed_restricted_recipe_reaches_its_machines(self):
        self.layer.git("mv", "recipes-x/bar/bar.bb", "recipes-x/bar/bar_2.0.bb")
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["reached"], j["global"]), (["b1"], []))

    def test_compatible_host_null_opened_per_override(self):
        self.layer.edit("recipes-x/hdr/hdr.bb", 'H = "1"', 'H = "2"')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["reached"], j["global"], j["uniform"]), (["p1", "p2"], [], []))

    def test_compatible_machine_invalid_opened_per_override(self):
        self.layer.edit("recipes-x/tee/tee.bb", 'T = "1"', 'T = "2"')
        self.layer.commit()
        self.assertEqual(self.layer.select()["reached"], ["b1"])

    def test_change_reaching_no_machine_is_reported(self):
        self.layer.edit("recipes-x/none/none.bb", 'N = "1"', 'N = "2"')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual(j["machines"], [])
        # the old and the new line, one report each
        self.assertEqual([r.split(":")[0] for r in j["nowhere"]], ["recipes-x/none/none.bb"] * 2)

    def test_fragment_is_not_exercised(self):
        self.layer.write("conf/fragments/x/y.conf", 'A = "1"\n')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["machines"], j["untested"]), ([], ["conf/fragments/x/y.conf"]))

    def test_comment_and_blank_lines_need_nothing(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'SUMMARY = "foo"\n', 'SUMMARY = "foo"\n# note\n\n')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["machines"], j["global"]), ([], []))

    def test_compatible_machine_scopes_an_unconditional_line(self):
        self.layer.edit("recipes-x/bar/bar.bb", 'V = "1"', 'V = "2"')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["reached"], j["global"]), (["b1"], []))
        # tune B has a single machine: the control comes from another tune
        self.assertEqual(j["machines"][0], "b1")
        self.assertGreaterEqual(len(j["machines"]), 2)

    def test_inc_is_scoped_by_the_recipes_requiring_it(self):
        self.layer.edit("recipes-x/baz/baz.inc", 'W = "1"', 'W = "2"')
        self.layer.commit()
        self.assertEqual(self.layer.select()["reached"], ["p1", "p2"])

    def test_machine_include_reaches_its_includers(self):
        self.layer.edit("conf/machine/include/soca.inc", 'X = "1"', 'X = "2"')
        self.layer.commit()
        self.assertEqual(self.layer.select()["reached"], ["a1", "a2"])

    def test_machine_conf_is_always_selected_first(self):
        self.layer.edit("conf/machine/p2.conf", "", 'Z = "1"\n')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual(j["machines"][0], "p2")
        self.assertEqual(j["roles"]["p2"]["role"], "changed")
        # p2's tune has another machine: it is the pair to compare against
        self.assertIn("p1", j["machines"])

    def test_class_is_scoped_by_its_inheriters(self):
        self.layer.edit("classes/k.bbclass", 'K = "1"', 'K = "2"')
        self.layer.commit()
        self.assertEqual(self.layer.select()["reached"], ["b1"])

    def test_class_inherited_globally_reaches_any_machine(self):
        self.layer.edit("conf/layer.conf", "\n", '\nINHERIT += "k"\n')
        self.layer.commit()
        self.layer.edit("classes/k.bbclass", 'K = "1"', 'K = "2"')
        self.layer.git("add", "-A")
        self.layer.git("commit", "-q", "--amend", "--no-edit")
        self.assertTrue(self.layer.select()["global"])

    def test_patch_follows_the_statement_naming_it(self):
        self.layer.edit("recipes-x/foo/files/fix.patch", "+++ b\n", "+++ b\n+x\n")
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["reached"], j["global"]), (["a1", "a2"], []))

    def test_unparsed_files_need_no_machine(self):
        self.layer.edit("README.md", "readme", "readme 2")
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual((j["machines"], j["global"]), ([], []))
        self.assertEqual(j["ignored"], ["README.md"])

    def test_dynamic_layer_is_reported_untested(self):
        self.layer.edit("dynamic-layers/other/recipes-y/y.bbappend", 'Y = "1"', 'Y = "2"')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual(j["machines"], [])
        self.assertEqual(j["untested"], ["dynamic-layers/other/recipes-y/y.bbappend"])

    def test_move_into_a_dynamic_layer_is_a_removal(self):
        self.layer.git("mv", "recipes-x/bar", "dynamic-layers/other/recipes-x-bar")
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual(j["reached"], ["b1"])
        self.assertEqual(j["untested"], ["dynamic-layers/other/recipes-x-bar/bar.bb"])

    def test_removed_lines_count(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'SRC_URI:append:soca = " file://fix.patch"\n', "")
        self.layer.commit()
        self.assertEqual(self.layer.select()["reached"], ["a1", "a2"])

    def test_compatible_machine_change_reaches_old_and_new_machines(self):
        self.layer.edit("recipes-x/bar/bar.bb", '"(socb)"', '"(soca)"')
        self.layer.commit()
        self.assertEqual(self.layer.select()["reached"], ["a1", "a2", "b1"])

    def test_narrowest_change_ranks_first_under_a_cap(self):
        # :fam reaches four machines, :ppc two: the cap keeps the ppc pair.
        self.layer.edit("recipes-x/foo/foo.bb", 'LICENSE = "MIT"\n',
                        'LICENSE = "MIT"\nF:fam = "1"\nG:ppc = "1"\n')
        self.layer.commit()
        j = self.layer.select("--max", "2")
        self.assertEqual(sorted(j["machines"]), ["p1", "p2"])
        self.assertTrue(j["capped"])

    def test_same_tune_pair_when_every_machine_is_reached(self):
        self.layer.edit("recipes-x/foo/foo.bb", 'LICENSE = "MIT"\n', 'LICENSE = "MIT"\nF:ppc = "1"\n')
        self.layer.commit()
        j = self.layer.select()
        self.assertEqual(j["machines"][:2], ["p1", "p2"])
        self.assertEqual(j["roles"]["p2"]["role"], "pair")


class Shards(unittest.TestCase):
    table = OrderedDict((m, {"tune": t, "overrides": []}) for m, t in (
        ("a1", "tunea"), ("a2", "tunea"), ("a3", "tunea"), ("a4", "tunea"),
        ("b1", "tuneb"), ("p1", "tunep"), ("p2", "tunep")))

    def shards(self, roles, size=4):
        picked = OrderedDict((m, (r, "")) for m, r in roles)
        return [(s["name"], s["machines"].split()) for s in am.shards(picked, self.table, size)]

    def test_one_run_per_tune_each_with_the_control(self):
        self.assertEqual(self.shards([("a1", "reached"), ("a2", "pair"), ("p1", "reached"),
                                      ("p2", "pair"), ("b1", "control")]),
                         [("tunea", ["a1", "a2", "b1"]), ("tunep", ["p1", "p2", "b1"])])

    def test_first_machine_anchors_without_a_control(self):
        self.assertEqual(self.shards([("a1", "reached"), ("a2", "pair"), ("p1", "reached")]),
                         [("tunea", ["a2", "a1"]), ("tunep", ["p1", "a1"])])

    def test_large_tune_is_chunked_around_its_tune_anchor(self):
        roles = [(m, "all") for m in ("b1", "a1", "a2", "a3", "a4")]
        self.assertEqual(self.shards(roles, size=3),
                         [("tunea-1", ["a1", "a2", "b1"]), ("tunea-2", ["a1", "a3", "b1"]),
                          ("tunea-3", ["a1", "a4", "b1"])])

    def test_every_machine_is_in_a_run(self):
        roles = [(m, "all") for m in self.table]
        got = {m for _, ms in self.shards(roles, size=3) for m in ms}
        self.assertEqual(got, set(self.table))

    def test_single_machine_and_empty(self):
        self.assertEqual(self.shards([("a1", "reached")]), [("tunea", ["a1"])])
        self.assertEqual(self.shards([]), [])


if __name__ == "__main__":
    unittest.main()
