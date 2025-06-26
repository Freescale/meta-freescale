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
# 1. Stable [linux-6.6.y] branch updates of korg;
# 2. NXP-specific updates via branch [lf-6.6.y] shared via GitHub NXP repo;
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
#    tag: v6.12.3
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: lf-6.12.3-1.0.0
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
# The list includes well-known commits not yet upstreamed. Reverts address merge
# conflicts, prioritizing NXP BSP source code as the latest vendor updates.
# Additional commits may exist to better acommodate yocto builds.
#
# $ git log --oneline  --no-merges v6.12.34.. ^mainline/linux-6.12.y ^NXP/lf-6.12.y
# - 8d94e52076a1 Revert "firmware: arm_scmi: Fix i.MX build dependency"
# - adefe4ecd9c6 Revert "PCI: imx6: Fix suspend/resume support on i.MX6QDL"
# - 2837cecae149 imx8mp-olimex.dts: CSI GPIO pins
# - 29efe53139ac hwrng: optee: support generic crypto
# - 7f80ce9b7c82 arm64: dts: imx8mq: drop cpu-idle-states
# - 5e3b6ecf85c4 of: enable using OF_DYNAMIC without OF_UNITTEST
# - 1bd794e571f6 gpu: drm: cadence: select hdmi helper
# - d74361dc9bdc imx:dts:imx8mm-evkb: fix the pmic name to avoid duplicated label error
# - 90900e871607 arm64: dts: imx8mm-evk-qca-wifi: enable support for bluetooth
# - 24ef2924dd5f drm: of: Fix build without CONFIG_OF
# - a97fe216ede5 i2c: imx: Remove unnecessary clock reconfiguration
# - 93e7f87e7bc6 drm/imx: lcdifv3: Fix videomode settings
# - ff2397021b0d clk: imx: imx8qm: add more resources to whitelist
# - e0fc64f7a2f8 arm64: dts: imx8: img: add #address-cells and #size-cells to I2C MIPI CSI nodes
# - 98b777bfa849 arm64: dts: imx8qm: add missing imx8-ss-cm40.dtsi include
# - 2d0aefc287a7 arm64: imx_v8_defconfig: Enable CONFIG_GPIO_VF610
# - 44aadbd00c61 imx8mp-olimex.dts: Olimex iMX8MP-SOM-EVB-IND
#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

require linux-imx.inc

KBRANCH = "6.12-1.0.x-imx"
SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"
SRCREV = "c63da44a120c9ad720877a74f0c94e2d9b08cab1"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.12.34"

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "imx_v8_defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "imx_v8_defconfig"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-lf-6.12.y"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
