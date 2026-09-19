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
#    tag: v6.6.147
#
# ------------------------------------------------------------------------------
# 2. NXP-specific (tag or SHA(s))
# ------------------------------------------------------------------------------
#    tag: lf-6.6.52-2.2.0
#
# ------------------------------------------------------------------------------
# 3. Critical patches (SHA(s))
# ------------------------------------------------------------------------------
# The list includes well-known commits not yet upstreamed. Reverts address merge
# conflicts, prioritizing NXP BSP source code as the latest vendor updates.
# Additional commits may exist to better acommodate yocto builds.
#
# $ git log --oneline  --no-merges v6.6.147.. ^mainline/linux-6.6.y ^NXP/lf-6.6.y
# - 9b87d01b354c LF-18121: firmware: imx: ele: read_common_fuse() resp-buf-sz handling
# - 89a6079731c6 arm64: dts: imx8x-mek: switch Type-C connector power-role to dual
# - 91aee5ea4eef media: imx8-isi: add RAW Bayer and grayscale pixel formats
# - e66e8eeb85d2 media: imx8-isi: fix source format negotiation
# - 6a81e01a84ac media: imx8mp-csi: clear GASKET data type before updating format
# - acc75ba2cc2d arm64: dts: imx8qm-mek: remove duplicated off-on-delay-us property
# - 4900511d8ebf spi: nxp-fspi: Add per_op_freq to quirks mem_caps
# - e59664173020 media: nxp: imx8-isi: Fix build error in mxc_isi_m2m_resume()
# - 501064c672a2 media: imx8-isi-m2m: fix mxc_isi_m2m_resume
# - 2baf5d2d5592 Revert "LF-12944 firmware: arm_scmi: bus: bypass set fwnode for scmi cpufreq"
# - ff2e4152c6ac Fix merge error on pci/controller/dwc/pci-imx6.c
# - 0e91a51824ac Fix the merge between 6.6.85 and imx branch
# - db393a056f28 imx8mp-olimex.dts: CSI GPIO pins
# - 8981bfbf2cd5 Reapply "LF-12740: mxc: vpu: hantro_v4l2: report performance statistics"
# - 9a97c180b1c4 Fix spi-nxp-fspi merge error
# - e587f8fe42f1 firmware: se_fw: remove info_list from ro section
# - 0f638960dcff media: Kconfig: fix double VIDEO_DEV
# - 198242c057e0 drivers:clk:imx:clk-imx8mp-audiomix: remove duplicated CLK_GATE_PARENT definition
# - 4f5936d7391f Revert "usb: gadget: u_serial: Disable ep before setting port to null to fix the crash caused by port being null"
# - 997b7e13e413 imx8mp-olimex.dts: Olimex iMX8MP-SOM-EVB-IND
# - b746c990ecba Revert "LF-12740: mxc: vpu: hantro_v4l2: report performance statistics"
# - e349e6c45a94 arm64: imx_v8_defconfig: Enable CONFIG_GPIO_VF610
# - 5a015324eddc arm64: dts: imx8qm: add missing imx8-ss-cm40.dtsi include
# - 8a8245d395d5 arm64: dts: imx8: img: add #address-cells and #size-cells to I2C MIPI CSI nodes
# - db13648c4be6 fw: imx: seco_mu: change dev_err to dev_err_probe for -EPROBE_DEFER
# - 0451236fd0ae clk: imx: imx8qm: add more resources to whitelist
# - 2ee789512d1b drm/imx: lcdifv3: Fix videomode settings
# - 5cd4c30ec228 i2c: imx: Remove unnecessary clock reconfiguration
# - 583f2a703c5d tty: vt: conmakehash: remove non-portable code printing comment header
# - 4ddc4dae8515 tty: vt: conmakehash: cope with abs_srctree no longer in env
# - 46a05495bce3 drm: of: Fix build without CONFIG_OF
# - 3d6392b96bf1 Revert "LF-4131 iio: gyro: fxas21002c: Fix raw data is not updated in trigger/buffer"
# - 93b9fc75becd nvmem: imx-ocotp-fsb-s400: BUG: Fix the word count
# - 090d101928fc tty: vt: conmakehash: Don't mention the full path of the input in output
# - d16eb5ced32f arm64: dts: imx8mm-evk-qca-wifi: enable support for bluetooth
# - d39502c0dea9 imx:dts:imx8mm-evkb: fix the pmic name to avoid duplicated label error
# - 58181fb0ff67 media: imx8: select v4l2_* for mxc-mipi-csi2_yav
# - 930431e0d1d5 gpu: drm: cadence: select hdmi helper
# - da675fd29502 of: enable using OF_DYNAMIC without OF_UNITTEST
# - 7c5f3cbb180c arm64: dts: imx8mq: drop cpu-idle-states
# - a9920ce3e197 hwrng: optee: support generic crypto
#
# NOTE to upgraders:
# This recipe should NOT collect individual patches, they should be applied to
# the linux-fslc kernel tree on the corresponding branch, and tracking
# information should be properly filled in above.
###############################################################################

require linux-imx.inc

KBRANCH = "6.6-2.2.x-imx"
SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"
SRCREV = "e74e930ce3ffcfaf8bc7d1c1cfde4709a645b8e3"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.6.147"

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "imx_v8_defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "imx_v8_defconfig"

# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-lf-6.6.y"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
