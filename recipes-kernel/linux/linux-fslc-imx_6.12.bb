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
# $ git log --oneline  --no-merges v6.12.49.. ^mainline/linux-6.12.y ^NXP/lf-6.12.y
# - bacd5504126bb imx8mp-olimex.dts: CSI GPIO pins
# - 3a7012e991c98 hwrng: optee: support generic crypto
# - 6c0a3377748eb arm64: dts: imx8mq: drop cpu-idle-states
# - 7db0692d9ff5e of: enable using OF_DYNAMIC without OF_UNITTEST
# - eff98b934385c gpu: drm: cadence: select hdmi helper
# - be5e175e43d93 imx:dts:imx8mm-evkb: fix the pmic name to avoid duplicated label error
# - 76e18f5a57b3e arm64: dts: imx8mm-evk-qca-wifi: enable support for bluetooth
# - 06b99391f850c drm: of: Fix build without CONFIG_OF
# - 17ac89e381a9d i2c: imx: Remove unnecessary clock reconfiguration
# - 6d157e81ccc53 drm/imx: lcdifv3: Fix videomode settings
# - 0a355239e2df3 clk: imx: imx8qm: add more resources to whitelist
# - c5c4869899b1c arm64: dts: imx8: img: add #address-cells and #size-cells to I2C MIPI CSI nodes
# - 3159e7d086295 arm64: dts: imx8qm: add missing imx8-ss-cm40.dtsi include
# - ffea393034d48 arm64: imx_v8_defconfig: Enable CONFIG_GPIO_VF610
# - a8762ad609202 imx8mp-olimex.dts: Olimex iMX8MP-SOM-EVB-IND
#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

require linux-imx.inc

KBRANCH = "6.12-2.0.x-imx"
SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"
SRCREV = "77b58422ab0fa4480a0b89a5f1ebfb9d1a900aef"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.12.49"

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "imx_v8_defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "imx_v8_defconfig"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-lf-6.12.y"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
