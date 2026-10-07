# check-layer helpers

## affected-machines.py

Picks the machines a `yocto-check-layer` run needs to exercise a change, so a
pull request can be checked in minutes instead of running every machine. The
full run over every machine stays the sign-off; this selection is a reduced
run by design, and its verdict covers only the machines it lists.

It reads the diff, not the build:

| Change | Machines it reaches |
| --- | --- |
| a line under a machine override (`SRC_URI:append:mx8-nxp-bsp`, `do_install:append:qoriq-ppc() { ... }`) | the machines whose `MACHINEOVERRIDES` carry it |
| an unconditional line in a recipe restricted by `COMPATIBLE_MACHINE` | the machines it is compatible with |
| an `.inc` or a class | the scope of the recipes that require or inherit it |
| `conf/machine/<machine>.conf` | that machine, always selected |
| `conf/machine/include/*.inc` | the machines that include it |
| a patch or installed file | wherever the statement naming it applies |
| any other line BitBake parses (`conf/layer.conf`, a python condition, an unrestricted recipe) | any machine: one per tune is sampled |
| documentation, CI, `contrib/`, `scripts/` | none |
| `dynamic-layers/` | none in this run (reported as not exercised); a recipe moved into or out of one counts as removed or added |

From the reached machines it keeps one per tune (`TUNE_PKGARCH`), then a
second machine of that tune, because `test_machine_signatures` compares
machines of one architecture with each other: an unreached machine if there
is one, else the reached machine least like the first (another SoC). It adds
a machine of another tune as a control for allarch and native recipes. Tunes
reached by the narrowest change rank first, so `--max` drops the weakest
evidence.

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

Tests (no BitBake, no network):

```sh
python3 -m unittest discover -s contrib/check-layer/tests
```
