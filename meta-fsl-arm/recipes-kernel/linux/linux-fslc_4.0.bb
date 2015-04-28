# Copyright (C) 2012-2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

include linux-fslc.inc

PV = "4.0+git${SRCPV}"

SRCBRANCH = "patches-4.0"
SRCREV = "ba621b5d76e2d4e480952d542f7c97d5c4b97994"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
