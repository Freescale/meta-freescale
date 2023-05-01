# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "Linux Kernel provided by NXP and supported by Community"
DESCRIPTION = "Linux Kernel provided by NXP as the part of release distribution. \
Main focus is set on i.MX Family Reference Boards. \
It includes support for many NXP Proprietary IPs (GPU, VPU, IPU). \
Latest stable Kernel patchlevel is applied and maintained by Community."

###############################################################################
# This recipe (and corresponding kernel repository and branch) receives updates
# from 3 different sources:
# 1. Stable [linux-6.1.y] branch updates of korg;
# 2. NXP-specific updates via branch [lf-6.1.y] shared via GitHub NXP repo;
# 3. Critical patches, which are not (yet) integrated into either of 2 above
#    sources, but are required to be applied to the kernel tree.
#
# Therefore, there is a need to keep track on the patches which are introduced
# from every source. This could be achieved in this recipe by filling the
# below list with the information once the update is performed from any source.
#
# Once the critical patch gets merged into the stable branch, or NXP-specific
# patches would be covered by the tag - individual entries from sections below
# could be removed.
#
# ------------------------------------------------------------------------------
# 1. Stable (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: v6.1.24
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: lf-6.1.1-1.0.0
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

require linux-imx.inc

DEPENDS += "lzop-native bc-native"

KBRANCH = "6.1-1.0.x-imx"
SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"
SRCREV = "897d73a656fe33d0c4549c5de1d07797f99d6871"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.1.24"

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "imx_v8_defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "imx_v8_defconfig"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-lf-6.1.y"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
