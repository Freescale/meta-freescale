# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

include linux-fslc.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

PV = "5.4.20+git${SRCPV}"

SRCBRANCH = "5.4.x+fslc"
SRCREV = "18d5e274c7807dd6eafa5cedf1ab528ac364fa55"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6|vf|use-mainline-bsp)"
