# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

include linux-fslc.inc

PV = "3.17+git${SRCPV}"

SRCBRANCH = "patches-3.17"
SRCREV = "bfe01a5ba2490f299e1d2d5508cbbbadd897bbe9"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
