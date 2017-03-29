# Copyright (C) 2012-2016 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

include linux-fslc.inc

PV = "4.4+git${SRCPV}"

SRCBRANCH = "4.4.x"
SRCREV = "f5ecad2b737be61e43e4971ab5388677a0b5dc8a"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6|vf)"
