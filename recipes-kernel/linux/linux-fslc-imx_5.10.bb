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
# 1. Stable [linux-5.10.y] branch updates of korg;
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
#    tag: v5.10.98
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: lf-5.10.52-2.1.0
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
#    27b6c760cc7f ("thermal: imx: fix a merging issue")
#    8ef27ae9f200 ("gpio: fix enabling GPIO_VF610")
#    12099c38577a ("ASoC: fsl_sai: Correct the clock source for mclk0")
#    db172377e6e2 ("Revert "MLK-12786-2: ASoC: fsl_sai: correct the clock source for mclk0"")
#    372825d96a29 ("ARM: dts: imx7s: fix ARM timer definition")
#    8b20421b2b61 ("staging: fsl_ppfe: reduce MAC address filtering loglevel to debug")
#    16ea8f2ab906 ("staging: fsl_ppfe: assign correct OF nodes to individual ports")
#    9d0f521803cd ("staging: fsl_ppfe: handle all RGMII modes in pfe_eth_adjust_link")
#    9d957f4a4a32 ("PCI: dwc: PCI_IMX6 must depend on PCIE_DW")
#    983b1aa2a2ea ("usb: chipidea: fix missing chipidea.h include")
#    bacde482ab6f ("ARM: imx: select MAILBOX with IMX_MBOX")
#    5fd63d48755a ("gpu: drm: allow enabling DRM_IMX on Layerscape SoCs")
#    b06df4cec270 ("ARM: dts: imx7s: revert LCDIF AXI clock to IMX7D_LCDIF_PIXEL_ROOT_CLK")
#    bdf733608e50 ("ARM: imx: add missing debug_ll_io_init to i.MX6UL map_io")
#    302f8a6d262a ("i2c: imx: remove unneeded libata include")
#    107dc9bc6283 ("i2c: imx: fix lockdep issue")
#    681f2d40b114 ("i2c: imx: avoid taking clk_prepare mutex in PM callbacks (again)")
#    351c82388784 ("arch/arm64/boot/dts/freescale: fix file permissions on dts files")
#    fb3e7593ee84 ("irq-imx-irqsteer: fix compile error if CONFIG_PM_SLEEP is not set")
#    96613ac4e960 ("arm: imx: include i.MX6SX DDR freq implementation for i.MX6UL")
#    36fea22d04f4 ("arm: imx: do not include smp_wfe_imx6.S for i.MX6SX")
#    f86c8a4736f2 ("arm: imx: enable HAVE_IMX_BUSFREQ for i.MX6")
#    05f7280c8648 ("arm: imx: do not build busfreq without HAVE_IMX_BUSFREQ")
#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

include linux-fslc.inc

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KBRANCH = "5.10-2.1.x-imx"
SRCREV = "13d6a9ec4e27f0d6567ca4e2c027e6652575aab0"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "5.10.98"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-5.10.52-2.1.0"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
