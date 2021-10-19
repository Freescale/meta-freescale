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
#    tag: v5.10.70
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: lf-5.10.52-2.1.0
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
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

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KBRANCH = "5.10-2.1.x-imx"
SRCREV = "d104abae22e109f942a3bb42ba4460e610c21965"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "5.10.70"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-5.10.52-2.1.0"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
