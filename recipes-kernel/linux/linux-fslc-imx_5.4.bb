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
# 2. NXP-specific updates via branch [5.4-2.2.x-imx] shared via CodeAurora forum;
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
#    tag: v5.4.118
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: rel_imx_5.4.70_2.3.2
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
#    b598b85172f7 irq-imx-irqsteer: fix compile error if CONFIG_PM_SLEEP is not set
#    845099bfd0b8 fbdev: fix fbinfo flag dropped upstream
#    847bfb09bb3b arm64: dts: imx8m: change ocotp node name on i.MX8M SoCs
#    d7ca6973c6d4 arm64: dts: imx8mm: Remove incorrect fallback compatible for ocotp
#    539d19f12066 arm: imx: include i.MX6SX DDR freq implementation for i.MX6UL
#    169b71ce3f8a arm: imx: do not include smp_wfe_imx6.S for i.MX6SX
#    60be4c625594 arm: imx: enable HAVE_IMX_BUSFREQ for i.MX6
#    f393781fa575 arm: imx: do not build busfreq without HAVE_IMX_BUSFREQ
#    e0e74faf6af4 ARM: dts: imx6ul-kontron-n6x1x-s: Remove an obsolete comment and fix indentation
#    ad24781aecc3 ARM: dts: imx6ul-kontron-n6x1x-s: Add vbus-supply and overcurrent polarity to usb nodes
#    d56aa09ed0e0 ARM: dts: imx6ul-kontron-n6x1x: Add 'chosen' node with 'stdout-path'
#    923bc9e57b24 ARM: dts: Add support for two more Kontron evalkit boards 'N6311 S' and 'N6411 S'
#    f952a6404d10 ARM: dts: imx6ul-kontron-n6310-s: Move common nodes to a separate file
#    1fdb6565a632 ARM: dts: Add support for two more Kontron SoMs N6311 and N6411
#    4fb833f01c30 ARM: dts: imx6ul-kontron-n6310: Move common SoM nodes to a separate file
#    54c975dabcde ARM: imx: Fix boot crash if ocotp is not found
#    1041cd02b33c ARM: imx: Correct ocotp id for serial number support of i.MX6ULL/ULZ SoCs
#    974b100d789c soc: imx-scu: Using existing serial_number instead of UID
#    6fbfed9afe99 soc: imx8: Using existing serial_number instead of UID
#    64d8ea803af5 ARM: imx: Add serial number support for i.MX6/7 SoCs
#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

include linux-fslc.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

KBRANCH = "5.4-2.3.x-imx"
SRCREV = "d1bb99cd3c58a5569908d8302d2dc2352c9e51dd"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "5.4.118"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-imx-5.4.70-2.3.0"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
