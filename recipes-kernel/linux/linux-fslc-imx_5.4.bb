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
# 1. Stable [linux-5.4.y] branch updates of korg;
# 2. NXP-specific updates via branch [5.4-2.1.x-imx] shared via CodeAurora forum;
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
#    tag: v5.4.65
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: rel_imx_5.4.24_2.1.0
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
#    c874333fa0be arm64: dts: imx8mp: Add fallback compatible to ocotp node
#    55abb34c9faf arm64: dts: imx8m: change ocotp node name on i.MX8M SoCs
#    df1f59fb613e arm64: dts: imx8mn: Use "fsl,imx8mm-ocotp" as ocotp's fallback compatible
#    7ad2a7f3fd6f arm64: dts: imx8mm: Remove incorrect fallback compatible for ocotp
#    959ad6073277 perf cs-etm: Move definition of 'traceid_list' global variable from header file
#    129a6fad43b8 arm: imx: include i.MX6SX DDR freq implementation for i.MX6UL
#    e85ce3157725 arm: imx: do not include smp_wfe_imx6.S for i.MX6SX
#    4f49200132e6 arm: imx: enable HAVE_IMX_BUSFREQ for i.MX6
#    5d229c51b5a5 arm: imx: do not build busfreq without HAVE_IMX_BUSFREQ
#    285869ec9087 ARM: dts: imx6ul-kontron-n6x1x-s: Remove an obsolete comment and fix indentation
#    f03791085754 ARM: dts: imx6ul-kontron-n6x1x-s: Add vbus-supply and overcurrent polarity to usb nodes
#    67e44952c670 ARM: dts: imx6ul-kontron-n6x1x: Add 'chosen' node with 'stdout-path'
#    d4b105f930e2 ARM: dts: Add support for two more Kontron evalkit boards 'N6311 S' and 'N6411 S'
#    e778467f65d0 ARM: dts: imx6ul-kontron-n6310-s: Move common nodes to a separate file
#    8b39f5e53831 ARM: dts: Add support for two more Kontron SoMs N6311 and N6411
#    3bee262233eb ARM: dts: imx6ul-kontron-n6310: Move common SoM nodes to a separate file
#    40a6a98b3f38 ARM: imx: Fix boot crash if ocotp is not found
#    8aad5031be00 ARM: imx: Correct ocotp id for serial number support of i.MX6ULL/ULZ SoCs
#    564409aa3b33 soc: imx-scu: Using existing serial_number instead of UID
#    0ee8e005bbfc soc: imx8: Using existing serial_number instead of UID
#    134cbb845fc7 ARM: imx: Add serial number support for i.MX6/7 SoCs
#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

include linux-fslc.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRCBRANCH = "5.4-2.1.x-imx"
SRCREV = "e176f4c52f8071238edbc93c858a94c3362c8b22"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "5.4.65"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-imx_5.4.24_2.1.0"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
