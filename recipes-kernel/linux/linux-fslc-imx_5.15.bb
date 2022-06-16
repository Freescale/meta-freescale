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
# 1. Stable [linux-5.15.y] branch updates of korg;
# 2. NXP-specific updates via branch [5.10-2.1.x-imx] shared via CodeAurora forum;
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
#    tag: v5.15.48
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: lf-5.15.5-1.0.0
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
#    aa0d6324254a ("ARM: imx_v6/v7: config: remove lzo compression")
#    78a61d752a68 ("staging: fsl_ppfe: reduce MAC address filtering loglevel to debug")
#    119775ff0b40 ("staging: fsl_ppfe: assign correct OF nodes to individual ports")
#    f696b8a03198 ("staging: fsl_ppfe: handle all RGMII modes in pfe_eth_adjust_link")
#    069a8e1cde93 ("PCI: dwc: PCI_IMX6 must depend on PCIE_DW")
#    ea15f0d0fd08 ("usb: chipidea: fix missing chipidea.h include")
#    a30d0e4c26da ("ARM: imx: select MAILBOX with IMX_MBOX")
#    162f5592d475 ("gpu: drm: allow enabling DRM_IMX on Layerscape SoCs")
#    af7065ca8e07 ("ARM: dts: imx7s: revert LCDIF AXI clock to IMX7D_LCDIF_PIXEL_ROOT_CLK")
#    df227a18343f ("ARM: imx: add missing debug_ll_io_init to i.MX6UL map_io")
#    c874471ab39e ("i2c: imx: remove unneeded libata include")
#    651e1f69ef7b ("i2c: imx: fix lockdep issue")
#    bee6315aac16 ("i2c: imx: avoid taking clk_prepare mutex in PM callbacks (again)")
#    6a8f8108c8ce ("arch/arm64/boot/dts/freescale: fix file permissions on dts files")
#    11f0faaa75ec ("irq-imx-irqsteer: fix compile error if CONFIG_PM_SLEEP is not set")
#    d17efc542bf0 ("arm: imx: include i.MX6SX DDR freq implementation for i.MX6UL")
#    d99fa7fc4edc ("arm: imx: do not include smp_wfe_imx6.S for i.MX6SX")
#    a5a2dd0a75fc ("arm: imx: enable HAVE_IMX_BUSFREQ for i.MX6")
#    f40d20ee44da ("arm: imx: do not build busfreq without HAVE_IMX_BUSFREQ")
#    96bb4e4ffa01 ("ARM: dts: imx7s: fix ARM timer definition")
#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

include linux-fslc.inc

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KBRANCH = "5.15-1.0.x-imx"
SRCREV = "b6597629c15cf4fc99dbcdaa079cb1b4c3e92f2e"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "5.15.48"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-5.15.5-1.0.0"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
