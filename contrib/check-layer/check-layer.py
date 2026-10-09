#!/usr/bin/env python3
"""Run yocto-check-layer, with two savings for CI.

    check-layer.py [--tests all|common|bsp] <yocto-check-layer arguments>

--tests runs only the common tests or only the BSP ones. The common tests and
the signatures they compare against ignore --machines, so a CI split into
runs per group of machines can run them once (`common`) and the machine tests
everywhere else (`bsp`), instead of repeating them in every run.

A world signature dump repeated with the same layers, configuration and
machine is reused when the earlier one succeeded: test_signatures and
test_world, then test_machine_signatures and test_machine_world, each ask for
the same one, once with -k and once without, and a dump that succeeded with
-k is the same without it.

Run it where yocto-check-layer would run: in a build directory set up by
oe-init-build-env, with the layer under test out of BBLAYERS.
"""

import argparse
import importlib.machinery
import importlib.util
import logging
import os
import shutil
import sys

# The test modules in scripts/lib/checklayer/cases for each --tests choice.
MODULES = {"common": ["common", "distro"], "bsp": ["bsp"]}


def conf_state(builddir):
    """What a world dump depends on in the build directory: its conf/ files."""
    conf = os.path.join(builddir, "conf")
    state = []
    for name in sorted(os.listdir(conf)):
        path = os.path.join(conf, name)
        if os.path.isfile(path) and not name.endswith(".backup"):
            with open(path, errors="replace") as f:
                state.append((name, f.read()))
    return tuple(state)


def reuse_signatures(checklayer, logger):
    """Make checklayer.get_signatures reuse a successful identical dump."""
    real_get, real_check = checklayer.get_signatures, checklayer.check_command
    done = {}
    last = {"ok": False}

    # get_signatures ignores a failed -k dump that still wrote signatures:
    # note whether the command succeeded.
    def check_command(error_msg, cmd, cwd=None):
        last["ok"] = False
        out = real_check(error_msg, cmd, cwd)
        last["ok"] = True
        return out

    def get_signatures(builddir, failsafe=False, machine=None, extravars=None):
        key = (conf_state(builddir), machine, extravars)
        if key in done:
            logger.info("Reusing the world signatures of MACHINE=%s" % (machine or "(default)"))
            sigs, tunes = done[key]
            return dict(sigs), {t: list(tasks) for t, tasks in tunes.items()}
        last["ok"] = False
        sigs, tunes = real_get(builddir, failsafe, machine, extravars)
        if last["ok"]:
            done[key] = (dict(sigs), {t: list(tasks) for t, tasks in tunes.items()})
        return sigs, tunes

    checklayer.check_command = check_command
    checklayer.get_signatures = get_signatures


def main():
    ap = argparse.ArgumentParser(description=__doc__.split("\n\n")[0], add_help=False)
    ap.add_argument("--tests", choices=("all", "common", "bsp"), default="all")
    args, rest = ap.parse_known_args()

    script = shutil.which("yocto-check-layer")
    if not script:
        sys.exit("check-layer.py: yocto-check-layer not found: source oe-init-build-env first")
    script = os.path.realpath(script)
    sys.path.append(os.path.join(os.path.dirname(script), "lib"))
    sys.argv = [script] + rest

    import checklayer
    # Before the script and the test cases import it by name. The script
    # gives this logger its handler.
    reuse_signatures(checklayer, logging.getLogger("yocto-check-layer"))

    loader = importlib.machinery.SourceFileLoader("yocto_check_layer", script)
    ycl = importlib.util.module_from_spec(importlib.util.spec_from_loader(loader.name, loader))
    loader.exec_module(ycl)

    if args.tests != "all":
        def test_layer(td, layer, test_software_layer_signatures):
            from checklayer.context import CheckLayerTestContext
            ycl.logger.info("Starting to analyze: %s (%s tests)" % (layer["name"], args.tests))
            ycl.logger.info("Distro: %s" % td["bbvars"]["DISTRO"])
            ycl.logger.info("-" * 70)
            tc = CheckLayerTestContext(td=td, logger=ycl.logger, layer=layer,
                                       test_software_layer_signatures=test_software_layer_signatures)
            tc.loadTests(ycl.CASES_PATHS, modules=MODULES[args.tests])
            # A run that loaded nothing would pass while checking nothing.
            if not tc.suites.countTestCases():
                raise RuntimeError("check-layer.py: no %s tests found" % args.tests)
            return tc.runTests()
        ycl.test_layer = test_layer

    if args.tests == "bsp":
        # The signatures without the layer are only for test_signatures.
        real = ycl.get_signatures

        def get_signatures(builddir, *a, **kw):
            if not a and not kw:
                ycl.logger.info("Skipped: only test_signatures uses them")
                return {}, {}
            return real(builddir, *a, **kw)
        ycl.get_signatures = get_signatures

    try:
        return ycl.main()
    except Exception:
        import traceback
        traceback.print_exc()
        return 1


if __name__ == "__main__":
    sys.exit(main())
