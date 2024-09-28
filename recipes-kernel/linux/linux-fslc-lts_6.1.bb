# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "FSL Community BSP Linux LTS based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on LTS kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in upstream \
and will not become part of a LTS version, or because it is not applicable for \
upstreaming in any form."

require linux-imx.inc

KERNEL_DEVICETREE_32BIT_COMPATIBILITY_UPDATE = "1"

SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.1.72"

KBRANCH = "6.1.x+fslc"
SRCREV = "b80c9dede70132da3ae7feb949fe0803aad3db24"

KBUILD_DEFCONFIG:mx5-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx5-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx5-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "defconfig"
KBUILD_DEFCONFIG:mxs-generic-bsp = "mxs_defconfig"
KBUILD_DEFCONFIG:vf-generic-bsp = "imx_v6_v7_defconfig"

LOCALVERSION = "-fslc"

COMPATIBLE_MACHINE = "(imx-generic-bsp)"
