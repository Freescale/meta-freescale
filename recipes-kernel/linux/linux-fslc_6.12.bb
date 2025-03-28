# Copyright (C) 2012-2024 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

require linux-imx.inc

SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"

DEPENDS += " \
    coreutils-native \
"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
#
# Linux kernel stable 6.12.20 changelog:
# https://cdn.kernel.org/pub/linux/kernel/v6.x/ChangeLog-6.12.20
LINUX_VERSION = "6.12.20"

KBRANCH = "6.12.x+fslc"
SRCREV = "844aa3471b97053b0a59f68d36dc6f796338b562"

KBUILD_DEFCONFIG:mx5-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "defconfig"
KBUILD_DEFCONFIG:mxs-generic-bsp = "mxs_defconfig"
KBUILD_DEFCONFIG:vf-generic-bsp = "imx_v6_v7_defconfig"

LOCALVERSION = "-fslc"

COMPATIBLE_MACHINE = "(imx-generic-bsp)"
