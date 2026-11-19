# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "Linux Kernel provided by NXP and supported by Community"
DESCRIPTION = "Linux Kernel provided by NXP as the part of release distribution. \
               Main focus is set on i.MX Family Reference Boards. \
               It includes support for many NXP Proprietary IPs (GPU, VPU, IPU). \
               Latest stable Kernel patchlevel is applied and maintained by Community."
HOMEPAGE = "https://github.com/Freescale/linux-fslc"
SECTION = "kernel"

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
#    tag: v6.18.56
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: lf-6.18.37-2.1.0
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
# The list includes well-known commits not yet upstreamed. Reverts address merge
# conflicts, prioritizing NXP BSP source code as the latest vendor updates.
# Additional commits may exist to better acommodate yocto builds.
#
# $ git log --oneline  --no-merges v6.18.56.. ^mainline/linux-6.18.y ^NXP/lf-6.18.y
# - ecf00fd8280f3 Remove imx7d_src_init function
# - e351e675ed4fd arm64: dts: imx8mp: align dts for Olimex with NXP's rename of gpu labels
# - a42563621d1d1 media: imx8-isi: add RAW Bayer and grayscale pixel formats
# - 4ae13bfc6ca2c media: imx8-isi: fix source format negotiation
# - ff80ed1598c4a media: imx8mp-csi: clear GASKET data type before updating format
# - e41fea806a2e9 arm64: dts: imx8mp: CANbus transciever control for Olimex
# - 5c928f76b2998 arm64: dts: imx8mp: Fix Olimex EVK/SOM CSI pins
# - 28ecd525460cd imx8mp-olimex.dts: Olimex iMX8MP-SOM-EVB-IND
# - 799b8cdc006e4 drm/imx: lcdifv3: Fix videomode settings
# - e8d7910da8fac drm: of: Fix build without CONFIG_OF
# - 900dbbfc0d8d8 arm64: dts: imx8mm-evk-qca-wifi: enable support for bluetooth
# - b94dd9478717e gpu: drm: cadence: select hdmi helper
# - 7f6f66990e131 of: enable using OF_DYNAMIC without OF_UNITTEST
# - 0e87674352943 arm64: dts: imx8mq: drop cpu-idle-states
# - 0190cfd1945a8 hwrng: optee: support generic crypto

#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

require linux-imx.inc

KBRANCH = "6.18-2.1.x-imx"
SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"
SRCREV = "4ce72be4ed55a5d6e1beba28d07078fa13742389"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.18.56"

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "imx_v8_defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "imx_v8_defconfig"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-lf-6.18.y"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
