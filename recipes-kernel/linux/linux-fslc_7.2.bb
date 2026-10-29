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
HOMEPAGE = "https://github.com/Freescale/linux-fslc"
SECTION = "kernel"

require linux-imx.inc

DEPENDS += "\
    coreutils-native \
"

SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
#
# Linux kernel stable 7.2.13 changelog:
# https://cdn.kernel.org/pub/linux/kernel/v7.x/ChangeLog-7.2.13
LINUX_VERSION = "7.2.13"

KBRANCH = "7.2.x+fslc"
SRCREV = "3c172eede0d6b889ead876f45cc843070209208f"

KBUILD_DEFCONFIG:mx5-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "defconfig"
KBUILD_DEFCONFIG:mxs-generic-bsp = "mxs_defconfig"
KBUILD_DEFCONFIG:vf-generic-bsp = "imx_v6_v7_defconfig"

LOCALVERSION = "-fslc"

COMPATIBLE_MACHINE = "(imx-generic-bsp)"
