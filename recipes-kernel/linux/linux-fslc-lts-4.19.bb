# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

include linux-fslc.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

PV = "4.19.78+git${SRCPV}"

SRCBRANCH = "4.19.x+fslc"
SRCREV = "bc38ce926f0800f19b7523cdb88aea1944e46c51"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6|vf|use-mainline-bsp)"
