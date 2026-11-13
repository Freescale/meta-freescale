# check-layer helpers

## affected-machines.py

Picks the machines a `yocto-check-layer` run needs to exercise a change, so a
pull request can be checked in minutes instead of running every machine. The
full run over every machine stays the sign-off; this selection is a reduced
run by design, and its verdict covers only the machines it lists.

It reads the diff, not the build:

| Change | Machines it reaches |
| --- | --- |
| `conf/machine/<machine>.conf` | that machine, always selected |
| a line under a machine override (`SRC_URI:append:mx8-nxp-bsp`, `do_install:append:qoriq-ppc() { ... }`) | the machines whose `MACHINEOVERRIDES` carry it, within the file's scope |
| a line in a recipe | the recipe's scope: what its `COMPATIBLE_MACHINE`/`COMPATIBLE_HOST` allow, wherever the recipe pulls them from |
| a line in an `.inc`, a class, a machine include | the union of the scopes of the recipes and machines pulling it in |
| a file in an override-named directory (`files/mx8mp-nxp-bsp/`) | the machines carrying that override |
| another file in a recipe directory (a patch) | as the recipe lines naming it, else as the recipes beside it |
| a line without a scope (an unrestricted recipe, a class a configuration inherits, `conf/layer.conf`) | any machine |
| documentation, CI, `contrib/`, `scripts/` | none |
| `dynamic-layers/`, `conf/fragments/` | none in this run (reported as not exercised) |

A recipe's scope is read as BitBake parses it: what it requires, includes
and inherits from this layer is inlined where it does, `${PN}`, `${PV}`,
`${BP}`, `${BPN}` and `${THISDIR}` are taken from the file name, and `=`, `?=`
and `??=` apply in order. A pull naming another variable counts as a user
of every file it could name, so a change there is never narrowed by it.

A renamed file counts as changed throughout: a recipe's version is in its
name, and a move into or out of `dynamic-layers/` adds or removes it.

The rules only narrow what BitBake itself can tell from a name: an override
or `COMPATIBLE_MACHINE`. They do not guess which variables differ between
machines, so a line they cannot place reaches every tune.

From the reached machines it keeps one per tune (`TUNE_PKGARCH`), then a
second machine of each tune, because `test_machine_signatures` compares
machines of one architecture with each other: an unreached machine if there
is one. Without a line reaching any machine, it adds a machine of another
tune as a control for allarch and native recipes. Under `--max`, tunes come
before second machines, since most `test_machine_world` failures show on a
single tune, and tunes reached by the narrowest change come first.

The machine table comes from BitBake, since `MACHINEOVERRIDES` is assembled
across several includes. Generate it in a build directory with this layer in
`BBLAYERS` (about six seconds per machine), and regenerate it whenever
`conf/machine/` changes:

```sh
contrib/check-layer/affected-machines.py table > machines.tsv
contrib/check-layer/affected-machines.py select --table machines.tsv \
    --base origin/master --max 16 --explain
yocto-check-layer-wrapper -- "$PWD" --no-auto-dependency \
    --machines $(contrib/check-layer/affected-machines.py select \
                 --table machines.tsv --base origin/master --max 16)
```

An empty selection means nothing BitBake parses changed: skip the run, since a
BSP layer checked without `--machines` silently skips its machine tests.

`--format shards` splits the selection into small runs for a CI matrix, as a
JSON list of `{name, machines}`, at most `--shard-size` machines each
(default 4). `test_machine_signatures` compares a task only between the
machines of one run, keyed by the task's tune, so every run keeps one machine
of its tune and one anchor machine (the control, else the first selected): a
machine-tuned task is still compared across its tune, and an allarch or
native task across every machine, through those two. A task whose tune spans
several machine tunes (a multilib's) is compared only within each run.
`--all` selects every machine in the table, for the full run.

## check-layer.py

Runs `yocto-check-layer` (same arguments), saving two kinds of repeated work:

- `--tests common` or `--tests bsp` runs only the common tests or only the
  machine ones. The common tests, and the signatures they compare against,
  ignore `--machines`: CI runs them once, and the machine tests in each run of
  machines.
- A world signature dump with the same layers, configuration and machine is
  reused when the earlier one succeeded: test_world reuses test_signatures',
  and test_machine_world reuses test_machine_signatures'.

Run it in a build directory from `oe-init-build-env`, as the wrapper would:

```sh
contrib/check-layer/check-layer.py --tests bsp "$PWD" --no-auto-dependency \
    --machines imx8mm-ddr4-evk imx8mp-ddr4-evk
```

Tests (no BitBake, no network):

```sh
python3 -m unittest discover -s contrib/check-layer/tests
```
