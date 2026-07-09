#!/bin/sh
# Run oelint-adv over meta-freescale. Requires oelint-adv on PATH.
#
# All linter configuration is declarative and lives at the layer root, so this
# script only has to enumerate the files (oelint-adv does not recurse into
# directories):
#   * .oelint.cfg           - sets '--release'; auto-loaded from the working
#                             directory
#   * oelint.constants.json - the layer constant-DB additions, passed via
#                             --constantmods for compatibility with oelint-adv
#                             versions that do not auto-load layer constants
#
# Exceptions should stay inline as '# nooelint: <rule.id>' comments next to the
# finding, so new recipes are fully linted and each exception is documented in
# place (and flagged by oelint.file.inlinesuppress_na once stale).
set -eu

# Neutralise CDPATH so 'cd' below can't print or jump to an unexpected dir.
unset CDPATH

here=$(cd -- "$(dirname -- "$0")" && pwd)
layer=$(cd -- "$here/../.." && pwd)

# Run from the layer root so '.oelint.cfg' is probed in the working directory
# and the relative constants path below is stable.
cd -- "$layer"

files=$(find . \
    \( -name '*.bb' -o -name '*.bbappend' -o -name '*.bbclass' -o -name '*.inc' \) \
    | sort)

# Run serially: oelint-adv's parallel workers race while loading the layer
# constants, intermittently emitting false "unknown variable/override" findings.
# Serial execution is deterministic. Pass '--jobs N' to override.
# Keep files before --constantmods: that option accepts one or more arguments
# and otherwise consumes the entire generated file list.
# shellcheck disable=SC2086
exec oelint-adv --jobs 1 $files --constantmods +oelint.constants.json "$@"
