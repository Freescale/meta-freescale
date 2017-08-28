# Copyright (C) 2012-2017 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}-4.9:"

include linux-fslc.inc

PV = "4.9+git${SRCPV}"

SRCBRANCH = "4.9.x+fslc"
SRCREV = "8f876e153b255daeb60833296d5ab213537f36a1"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6|vf|use-mainline-bsp)"
