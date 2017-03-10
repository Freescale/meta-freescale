# Copyright (C) 2012-2017 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

include linux-fslc.inc

PV = "4.9+git${SRCPV}"

SRCBRANCH = "4.9.x+fslc"
SRCREV = "c3825da143fc419e2639e602f62d793ed0de4657"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6|vf)"
