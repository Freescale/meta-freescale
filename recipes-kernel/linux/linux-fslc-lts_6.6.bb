# Copyright (C) 2012-2024 O.S. Systems Software LTDA.
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

SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.6.50"

KBRANCH = "6.6.x+fslc"
SRCREV = "2473dfed9007471b503efe1f7de74d63eaf4c699"

KBUILD_DEFCONFIG:mx5-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v6_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "defconfig"
KBUILD_DEFCONFIG:mxs-generic-bsp = "mxs_defconfig"
KBUILD_DEFCONFIG:vf-generic-bsp = "imx_v6_v7_defconfig"

LOCALVERSION = "-fslc"

COMPATIBLE_MACHINE = "(imx-generic-bsp)"
