# Provides the i.MX common settings

include conf/machine/include/fsl-default-settings.inc
include conf/machine/include/fsl-default-versions.inc

require conf/machine/include/utilities.inc

# Machines or distros can define which BSP it should use by default. We are
# intending to default for mainline BSP by default and specific machines or
# DISTROs might change it if need.
#
# Two values are considered valid: mainline, nxp
IMX_DEFAULT_BSP ?= "mainline"

# Those are SoC families we'd like to force the use of mainline BSP.
IMX_DEFAULT_BSP:mxs ?= "mainline"
IMX_DEFAULT_BSP:mx5 ?= "mainline"

MACHINEOVERRIDES =. "use-${IMX_DEFAULT_BSP}-bsp:"

# UBOOT_BINARY is used inside the wks files to dynamically find the required
# U-Boot file.
UBOOT_BINARY ?= "u-boot.${UBOOT_SUFFIX}"

# Using the 'IMX_DEFAULT_BOOTLOADER' the machine can support multiple bootloader
# versions. This is done for NXP reference board where we support 'u-boot-fslc'
# and 'u-boot-imx'.
#
# So, for example in imx6qdlsabresd, we support both flavor and for this we
# define:
#
# ,----[ imx6qdlsabresd.conf ]
# | ### u-boot-fslc settings ###
# |
# | SPL_BINARY:pn-u-boot-fslc = "SPL"
# | UBOOT_MACHINE:pn-u-boot-fslc ?= "mx6sabresd_defconfig"
# | UBOOT_SUFFIX:pn-u-boot-fslc = "img"
# |
# | ### u-boot-imx settings ###
# |
# | # The u-boot-imx does not provide unified functionality for DL/Q/QP SoC
# | # variants. Change the defconfig to the targeted SoC variant.
# | UBOOT_MACHINE:pn-u-boot-imx ?= "mx6qsabresd_defconfig"
# | UBOOT_SUFFIX:pn-u-boot-imx = "imx"
# `----
#
# As result, the 'UBOOT_SUFFIX' is dynamically set based on the preferred U-Boot
# flavor to use.
#
# For machines where one of the flavors is required, we can force it. An example
# is the imx53qsb, which we define:
#
# ,----[ imx53qsb.conf ]
# | # This machine is not supported by u-boot-imx as it is not tested by NXP on this
# | # board. So we force it to use u-boot-fslc which is based on mainline here.
# | IMX_DEFAULT_BOOTLOADER = "u-boot-fslc"
# |
# | UBOOT_MAKE_TARGET = "u-boot.imx"
# | UBOOT_SUFFIX = "imx"
# |
# | UBOOT_MACHINE = "mx53loco_config"
# `----
#
# Please note that UBOOT_SUFFIX must be set in the machine config even
# if the value is the same for the U-Boot flavors. If the value is not
# set, then the assignment here is the one used, the value resolves to
# empty, and the build fails:
# | cp: cannot stat '/.../tmp/work/imx8qm_mek-fsl-linux/u-boot-imx/2022.04-r0/build/imx8qm_mek_defconfig/u-boot.': No such file or directory
#| WARNING: /.../tmp/work/imx8qm_mek-fsl-linux/u-boot-imx/2022.04-r0/temp/run.do_compile.21223:186 exit 1 from 'cp /.../tmp/work/imx8qm_mek-fsl-linux/u-boot-imx/2022.04-r0/build/${config}/${binary} /.../tmp/work/imx8qm_mek-fsl-linux/u-boot-imx/2022.04-r0/build/${config}/u-boot-${type}.${UBOOT_SUFFIX:pn-u-boot-imx}'
#
IMX_DEFAULT_BOOTLOADER ??= "u-boot-fslc"
UBOOT_SUFFIX ?= "${UBOOT_SUFFIX:pn-${IMX_DEFAULT_BOOTLOADER}}"

# We need to export the original variable to allow it to be used when generating
# wic based images.
SPL_BINARY ??= "${@get_spl_binary(d)}"

IMX_DEFAULT_MFGTOOL = "${@bb.utils.contains('IMX_DEFAULT_BOOTLOADER', 'u-boot-imx','u-boot-imx-mfgtool', 'u-boot-fslc', d)}"

PREFERRED_PROVIDER_u-boot ??= "${IMX_DEFAULT_BOOTLOADER}"
PREFERRED_PROVIDER_u-boot-mfgtool ??= "${IMX_DEFAULT_MFGTOOL}"
PREFERRED_PROVIDER_virtual/bootloader ??= "${IMX_DEFAULT_BOOTLOADER}"

PREFERRED_PROVIDER_u-boot-mxsboot-native ??= "u-boot-fslc-mxsboot-native"

UBOOT_ENTRYPOINT:mxs-generic-bsp    = "0x40008000"
UBOOT_ENTRYPOINT:mx51-generic-bsp   = "0x90008000"
UBOOT_ENTRYPOINT:mx53-generic-bsp   = "0x70008000"
UBOOT_ENTRYPOINT:mx6-generic-bsp    = "0x10008000"
UBOOT_ENTRYPOINT:mx6sl-generic-bsp  = "0x80008000"
UBOOT_ENTRYPOINT:mx6sll-generic-bsp = "0x80008000"
UBOOT_ENTRYPOINT:mx6sx-generic-bsp  = "0x80008000"
UBOOT_ENTRYPOINT:mx6ul-generic-bsp  = "0x80008000"
UBOOT_ENTRYPOINT:mx6ull-generic-bsp = "0x80008000"
UBOOT_ENTRYPOINT:mx6ulz-generic-bsp = "0x80008000"
UBOOT_ENTRYPOINT:mx7-generic-bsp    = "0x80008000"
UBOOT_ENTRYPOINT:mx7ulp-generic-bsp = "0x60008000"
UBOOT_ENTRYPOINT:mx8m-generic-bsp   = "0x40480000"
UBOOT_ENTRYPOINT:vf-generic-bsp     = "0x80008000"

# Some derivates can utilize the boot container provided by U-Boot,
# below variable sets that those machines which have a imx-boot-container
# in their MACHINEOVERRIDES can inherit a imx-boot-container class
UBOOT_PROVIDES_BOOT_CONTAINER = "0"
UBOOT_PROVIDES_BOOT_CONTAINER:imx-boot-container = "1"

# Trusted Firmware for Cortex-A (TF-A) can have different providers, either
# from upstream or from NXP downstream fork. Below variable defines which TF-A
# shall be taken into the build, and will be integrated into runtime image.
#
# Upstream TF-A recipe resides in the meta-arm layer and in maintained by OE
# community. Therefore, in order to add upstream TF-A - additional layer has
# to be included in the bblayers.con file. Compatible machines are added to
# this layer via dynamic-layers mechanism.
#
# NOTE: Current upstream TF-A version (v2.7) does not support HAB feature of
# i.MX8M family. If the upstream TF-A version is chosen, then HAB will not be
# available for all SoCs that are opting-in. This might change with future TF-A
# release, so this statement shall be kept here until support is added.
#
# Default TF-A provider to NXP downstream fork
IMX_DEFAULT_ATF_PROVIDER ??= "imx-atf"

# Allow setting the UART used during the boot by ATF.

# FIXME: We should return INVALID here but currently only i.MX8M has support to override the UART
# base address in source code.
SOC_ATF_BOOT_UART_BASE                    = ""

SOC_ATF_BOOT_UART_BASE:mx8m-generic-bsp   = "0x30890000"
ATF_BOOT_UART_BASE                       ?= "${SOC_ATF_BOOT_UART_BASE}"

PREFERRED_PROVIDER_virtual/xserver      = "xserver-xorg"
XSERVER_DRIVER                          = "xf86-video-fbdev"
XSERVER_DRIVER:vf-generic-bsp           = "xf86-video-modesetting"
XSERVER_DRIVER:append:mx8-generic-bsp   = " xf86-video-modesetting"
XSERVER_DRIVER:imx-mainline-bsp = " \
    xf86-video-fbdev \
    xf86-video-modesetting \
    xserver-xorg-extension-glx \
"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           ${XSERVER_DRIVER}"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

# Tunes for hard/soft float-point selection. Note that we allow building for
# thumb support giving distros the chance to enable thumb by setting
# ARM_INSTRUCTION_SET = "thumb"
#
# handled by software
# DEFAULTTUNE:mx6-generic-bsp ?= "cortexa9t-neon"
# handled by hardware
DEFAULTTUNE:mx6-generic-bsp    ?= "cortexa9thf-neon"
DEFAULTTUNE:mx6ul-generic-bsp  ?= "cortexa7thf-neon"
DEFAULTTUNE:mx6ull-generic-bsp ?= "cortexa7thf-neon"
DEFAULTTUNE:mx6ulz-generic-bsp ?= "cortexa7thf-neon"
DEFAULTTUNE:mx7-generic-bsp    ?= "cortexa7thf-neon"
DEFAULTTUNE:vf-generic-bsp     ?= "cortexa5thf-neon"

DEFAULTTUNE:mx8m-generic-bsp   ?= "cortexa53-crypto"
DEFAULTTUNE:mx8qm-generic-bsp  ?= "cortexa72-cortexa53-crypto"
DEFAULTTUNE:mx8x-generic-bsp   ?= "cortexa35-crypto"

INHERIT += "machine-overrides-extender"

#######
### NXP BSP specific overrides
#######

MACHINEOVERRIDES_EXTENDER:mx6q:use-nxp-bsp   = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxipu:imxvpu:imxgpu:imxgpu2d:imxgpu3d:mx6-generic-bsp:mx6-nxp-bsp:mx6q-generic-bsp:mx6q-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx6dl:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:imxipu:imxvpu:imxgpu:imxgpu2d:imxgpu3d:imxepdc:mx6-generic-bsp:mx6-nxp-bsp:mx6dl-generic-bsp:mx6dl-nxp-bsp"

MACHINEOVERRIDES_EXTENDER:mx6sx:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:imxgpu:imxgpu2d:imxgpu3d:mx6-generic-bsp:mx6-nxp-bsp:mx6sx-generic-bsp:mx6sx-nxp-bsp"

MACHINEOVERRIDES_EXTENDER:mx6sl:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:imxgpu:imxgpu2d:imxepdc:mx6-generic-bsp:mx6-nxp-bsp:mx6sl-generic-bsp:mx6sl-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx6sll:use-nxp-bsp = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:imxepdc:mx6-generic-bsp:mx6-nxp-bsp:mx6sl-generic-bsp:mx6sl-nxp-bsp:mx6sll-generic-bsp:mx6sll-nxp-bsp"

MACHINEOVERRIDES_EXTENDER:mx6ul:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:mx6-generic-bsp:mx6-nxp-bsp:mx6ul-generic-bsp:mx6ul-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx6ull:use-nxp-bsp = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:imxepdc:mx6-generic-bsp:mx6-nxp-bsp:mx6ul-generic-bsp:mx6ul-nxp-bsp:mx6ull-generic-bsp:mx6ull-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx6ulz:use-nxp-bsp = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:imxepdc:mx6-generic-bsp:mx6-nxp-bsp:mx6ul-generic-bsp:mx6ul-nxp-bsp:mx6ull-generic-bsp:mx6ull-nxp-bsp:mx6ulz-generic-bsp:mx6ulz-nxp-bsp"

MACHINEOVERRIDES_EXTENDER:mx7d:use-nxp-bsp   = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:imxepdc:mx7-generic-bsp:mx7-nxp-bsp:mx7d-generic-bsp:mx7d-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx7ulp:use-nxp-bsp = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:imxpxp:imxgpu:imxgpu2d:imxgpu3d:mx7-generic-bsp:mx7-nxp-bsp:mx7ulp-generic-bsp:mx7ulp-nxp-bsp"

MACHINEOVERRIDES_EXTENDER:vf:use-nxp-bsp     = "imx-generic-bsp:imx-nxp-bsp:vf-generic-bsp:vf-nxp-bsp"

MACHINEOVERRIDES_EXTENDER:mx8qm:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxdrm:imxdpu:imxgpu:imxgpu2d:imxgpu3d:imxvulkan:mx8-generic-bsp:mx8-nxp-bsp:mx8qm-generic-bsp:mx8qm-nxp-bsp"

MACHINEOVERRIDES_EXTENDER:mx8mm:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxdrm:imxvpu:imxgpu:imxgpu2d:imxgpu3d:mx8-generic-bsp:mx8-nxp-bsp:mx8m-generic-bsp:mx8m-nxp-bsp:mx8mm-generic-bsp:mx8mm-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx8mn:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxdrm:imxgpu:imxgpu3d:imxvulkan:mx8-generic-bsp:mx8-nxp-bsp:mx8m-generic-bsp:mx8m-nxp-bsp:mx8mn-generic-bsp:mx8mn-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx8mp:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxdrm:imxvpu:imxgpu:imxgpu2d:imxgpu3d:imxvulkan:mx8-generic-bsp:mx8-nxp-bsp:mx8m-generic-bsp:mx8m-nxp-bsp:mx8mp-generic-bsp:mx8mp-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx8mq:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxdrm:imxvpu:imxgpu:imxgpu3d:imxvulkan:mx8-generic-bsp:mx8-nxp-bsp:mx8m-generic-bsp:mx8m-nxp-bsp:mx8mq-generic-bsp:mx8mq-nxp-bsp"

MACHINEOVERRIDES_EXTENDER:mx8qxp:use-nxp-bsp = "imx-generic-bsp:imx-nxp-bsp:imxdrm:imxdpu:imxgpu:imxgpu2d:imxgpu3d:imxvulkan:mx8-generic-bsp:mx8-nxp-bsp:mx8x-generic-bsp:mx8x-nxp-bsp:mx8qxp-generic-bsp:mx8qxp-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx8dx:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxdrm:imxdpu:imxgpu:imxgpu2d:imxgpu3d:imxvulkan:mx8-generic-bsp:mx8-nxp-bsp:mx8x-generic-bsp:mx8x-nxp-bsp:mx8dx-generic-bsp:mx8dx-nxp-bsp"
MACHINEOVERRIDES_EXTENDER:mx8dxl:use-nxp-bsp = "imx-generic-bsp:imx-nxp-bsp:imxfbdev:mx8dxl-generic-bsp:mx8-generic-bsp:mx8-nxp-bsp:mx8x-generic-bsp:mx8x-nxp-bsp:mx8dxl-nxp-bsp"

#######
### Mainline BSP specific overrides
#######

MACHINEOVERRIDES_EXTENDER:mx23:use-mainline-bsp   = "imx-generic-bsp:imx-mainline-bsp:mxs-generic-bsp:mxs-mainline-bsp:mx23-generic-bsp:mx23-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx28:use-mainline-bsp   = "imx-generic-bsp:imx-mainline-bsp:mxs-generic-bsp:mxs-mainline-bsp:mx28-generic-bsp:mx28-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx27:use-mainline-bsp   = "imx-generic-bsp:imx-mainline-bsp:mx27-generic-bsp:mx27-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx51:use-mainline-bsp   = "imx-generic-bsp:imx-mainline-bsp:mx5-generic-bsp:mx5-mainline-bsp:mx51-generic-bsp:mx51-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx53:use-mainline-bsp   = "imx-generic-bsp:imx-mainline-bsp:mx5-generic-bsp:mx5-mainline-bsp:mx53-generic-bsp:mx53-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx6q:use-mainline-bsp   = "imx-generic-bsp:imx-mainline-bsp:mx6-generic-bsp:mx6-mainline-bsp:mx6q-generic-bsp:mx6q-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx6dl:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx6-generic-bsp:mx6-mainline-bsp:mx6dl-generic-bsp:mx6dl-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx6sx:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx6-generic-bsp:mx6-mainline-bsp:mx6sx-generic-bsp:mx6sx-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx6sl:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx6-generic-bsp:mx6-mainline-bsp:mx6sl-generic-bsp:mx6sl-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx6sll:use-mainline-bsp = "imx-generic-bsp:imx-mainline-bsp:mx6-generic-bsp:mx6-mainline-bsp:mx6sll-generic-bsp:mx6sll-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx6ul:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx6-generic-bsp:mx6-mainline-bsp:mx6ul-generic-bsp:mx6ul-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx6ull:use-mainline-bsp = "imx-generic-bsp:imx-mainline-bsp:mx6-generic-bsp:mx6-mainline-bsp:mx6ul-generic-bsp:mx6ul-mainline-bsp:mx6ull-generic-bsp:mx6ull-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx6ulz:use-mainline-bsp = "imx-generic-bsp:imx-mainline-bsp:mx6-generic-bsp:mx6-mainline-bsp:mx6ul-generic-bsp:mx6ul-mainline-bsp:mx6ull-generic-bsp:mx6ull-mainline-bsp:mx6ulz-generic-bsp:mx6ulz-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx7d:use-mainline-bsp   = "imx-generic-bsp:imx-mainline-bsp:mx7-generic-bsp:mx7-mainline-bsp:mx7d-generic-bsp:mx7d-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx7ulp:use-mainline-bsp = "imx-generic-bsp:imx-mainline-bsp:mx7-generic-bsp:mx7-mainline-bsp:mx7ulp-generic-bsp:mx7ulp-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:vf:use-mainline-bsp     = "imx-generic-bsp:imx-mainline-bsp:vf-generic-bsp:vf-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx8qm:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx8-generic-bsp:mx8-mainline-bsp:mx8qm-generic-bsp:mx8qm-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx8mm:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx8-generic-bsp:mx8-mainline-bsp:mx8m-generic-bsp:mx8m-mainline-bsp:mx8mm-generic-bsp:mx8mm-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx8mn:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx8-generic-bsp:mx8-mainline-bsp:mx8m-generic-bsp:mx8m-mainline-bsp:mx8mn-generic-bsp:mx8mn-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx8mp:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx8-generic-bsp:mx8-mainline-bsp:mx8m-generic-bsp:mx8m-mainline-bsp:mx8mp-generic-bsp:mx8mp-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx8mq:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx8-generic-bsp:mx8-mainline-bsp:mx8m-generic-bsp:mx8m-mainline-bsp:mx8mq-generic-bsp:mx8mq-mainline-bsp"

MACHINEOVERRIDES_EXTENDER:mx8qxp:use-mainline-bsp = "imx-generic-bsp:imx-mainline-bsp:mx8-generic-bsp:mx8-mainline-bsp:mx8x-generic-bsp:mx8x-mainline-bsp:mx8qxp-generic-bsp:mx8qxp-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx8dx:use-mainline-bsp  = "imx-generic-bsp:imx-mainline-bsp:mx8-generic-bsp:mx8-mainline-bsp:mx8x-generic-bsp:mx8x-mainline-bsp:mx8dx-generic-bsp:mx8dx-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx8dxl:use-mainline-bsp = "imx-generic-bsp:imx-mainline-bsp:mx8-generic-bsp:mx8-mainline-bsp:mx8x-generic-bsp:mx8x-mainline-bsp:mx8dxl-generic-bsp:mx8dxl-mainline-bsp"

MACHINEOVERRIDES_EXTENDER_FILTER_OUT = " \
    mx6 \
    mx6q \
    mx6dl \
    mx6sx \
    mx6sl \
    mx6sll \
    mx6ul \
    mx6ull \
    mx6ulz \
    \
    mx7 \
    mx7d \
    mx7ulp \
    \
    vf \
    \
    mx8 \
    mx8m \
    mx8qm \
    mx8mm \
    mx8mn \
    mx8mp \
    mx8mq \
    mx8qxp \
    mx8dx \
    mx8dxl \
"

MACHINEOVERRIDES_FILTERED_OUT_QA_ERROR = "%s overrides cannot be used and need conversion to use the new BSP-specific overrides. Check 'meta-freescale/scripts/convert-bsp-specific-overrides'."

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""
MACHINE_SOCARCH_SUFFIX:mx6q-nxp-bsp = "-mx6qdl"
MACHINE_SOCARCH_SUFFIX:mx6dl-nxp-bsp = "-mx6qdl"
MACHINE_SOCARCH_SUFFIX:mx6sx-nxp-bsp = "-mx6sx"
MACHINE_SOCARCH_SUFFIX:mx6sl-nxp-bsp = "-mx6sl"
MACHINE_SOCARCH_SUFFIX:mx6sll-nxp-bsp= "-mx6sll"
MACHINE_SOCARCH_SUFFIX:mx7d-nxp-bsp = "-mx7d"
MACHINE_SOCARCH_SUFFIX:mx7ulp-nxp-bsp = "-mx7ulp"
MACHINE_SOCARCH_SUFFIX:vf-nxp-bsp = "-vf"
MACHINE_SOCARCH_SUFFIX:mx6ul-nxp-bsp  = "-mx6ul"
MACHINE_SOCARCH_SUFFIX:mx6ull-nxp-bsp = "-mx6ul"
MACHINE_SOCARCH_SUFFIX:mx6ulz-nxp-bsp = "-mx6ul"
MACHINE_SOCARCH_SUFFIX:mx8qm-nxp-bsp  = "-mx8"
MACHINE_SOCARCH_SUFFIX:mx8mm-nxp-bsp  = "-mx8mm"
MACHINE_SOCARCH_SUFFIX:mx8mn-nxp-bsp  = "-mx8mn"
MACHINE_SOCARCH_SUFFIX:mx8mp-nxp-bsp  = "-mx8mp"
MACHINE_SOCARCH_SUFFIX:mx8mq-nxp-bsp  = "-mx8m"
MACHINE_SOCARCH_SUFFIX:mx8qxp-nxp-bsp = "-mx8"
MACHINE_SOCARCH_SUFFIX:mx8dx-nxp-bsp  = "-mx8"
MACHINE_SOCARCH_SUFFIX:mx8dxl-nxp-bsp = "-mx8xl"

# For Mainline we use a single SoC suffix as we don't have different build options
MACHINE_SOCARCH_SUFFIX:imx-mainline-bsp = "-imx"

MACHINE_ARCH_FILTER = "virtual/kernel"
MACHINE_SOCARCH_FILTER:append:imx-nxp-bsp = " \
    alsa-lib \
    gstreamer1.0 \
    weston \
"
MACHINE_SOCARCH_FILTER:append:imxvpu = " \
    imx-codec \
    imx-vpuwrap \
    libimxvpuapi \
    virtual/imxvpu \
"
MACHINE_SOCARCH_FILTER:append:imxvulkan = " \
    vulkan-headers \
    vulkan-loader \
    vulkan-tools \
"
MACHINE_SOCARCH_FILTER:append:imxgpu = " \
    virtual/egl \
    virtual/mesa \
    virtual/libopenvg \
    libdrm \
    cairo \
    libgal-imx \
    opencv \
    pango \
"
MACHINE_SOCARCH_FILTER:append:imxgpu2d = " \
    virtual/libg2d \
"
MACHINE_SOCARCH_FILTER:append:imxgpu3d = " \
    virtual/libgl \
    virtual/libgles1 \
    virtual/libgles2 \
"
MACHINE_SOCARCH_FILTER:append:imx-mainline-bsp = " \
    virtual/egl \
    virtual/libopenvg \
    virtual/libg2d \
    virtual/libgl \
    virtual/libgles1 \
    virtual/libgles2 \
    virtual/mesa \
    cairo \
    pango \
    qtbase \
"
MACHINE_SOCARCH_FILTER:append:mx6q-nxp-bsp = " \
    opencl-icd-loader \
    opencl-clhpp \
    opencl-headers \
"
MACHINE_SOCARCH_FILTER:append:mx8-nxp-bsp = " \
    opencl-icd-loader \
    opencl-clhpp \
    opencl-headers \
"
MACHINE_SOCARCH_FILTER:append:mx8qm-nxp-bsp = " \
    virtual/libopenvx \
"

INHERIT += "fsl-dynamic-packagearch"

SIGGEN_EXCLUDE_SAFE_RECIPE_DEPS:append = " \
    imx-gpu-viv->kernel-module-imx-gpu-viv \
    libimxvpuapi->virtual/imxvpu \
    imx-vpuwrap->virtual/imxvpu \
    imx-codec->virtual/imxvpu \
    imx-test->virtual/imxvpu \
"

# Firmware used for boot.
IMX_EXTRA_FIRMWARE                  ?= ""
IMX_EXTRA_FIRMWARE:mx8-generic-bsp   = "firmware-imx-8 imx-sc-firmware imx-seco"
IMX_EXTRA_FIRMWARE:mx8m-generic-bsp  = "firmware-imx-8m"
IMX_EXTRA_FIRMWARE:mx8x-generic-bsp  = "imx-sc-firmware imx-seco"

# Firmware
MACHINE_FIRMWARE ?= ""
MACHINE_FIRMWARE:append:mx27-generic-bsp   = " firmware-imx-vpu-imx27"
MACHINE_FIRMWARE:append:mx7d-generic-bsp   = " linux-firmware-imx-sdma-imx7d firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx6-generic-bsp    = " linux-firmware-imx-sdma-imx6q"
MACHINE_FIRMWARE:append:mx6q-generic-bsp   = " firmware-imx-vpu-imx6q"
MACHINE_FIRMWARE:append:mx6dl-generic-bsp  = " firmware-imx-vpu-imx6d firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx6sl-generic-bsp  = " firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx6sll-generic-bsp = " firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx6ull-generic-bsp = " firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx53-generic-bsp   = " firmware-imx-vpu-imx53 firmware-imx-sdma-imx53"
MACHINE_FIRMWARE:append:mx51-generic-bsp   = " firmware-imx-vpu-imx51 firmware-imx-sdma-imx51"
MACHINE_FIRMWARE:append:mx8mm-generic-bsp  = " linux-firmware-imx-sdma-imx7d"
MACHINE_FIRMWARE:append:mx8mn-generic-bsp  = " linux-firmware-imx-sdma-imx7d"
MACHINE_FIRMWARE:append:mx8mp-generic-bsp  = " linux-firmware-imx-sdma-imx7d firmware-imx-easrc-imx8mn firmware-imx-xcvr-imx8mp firmware-sof-imx"
MACHINE_FIRMWARE:append:mx8mq-generic-bsp  = " linux-firmware-imx-sdma-imx7d"
MACHINE_FIRMWARE:append:mx8qm-generic-bsp  = " firmware-imx-vpu-imx8"
MACHINE_FIRMWARE:append:mx8qxp-generic-bsp = " firmware-imx-vpu-imx8"
MACHINE_FIRMWARE:append:mx8dx-generic-bsp  = " firmware-imx-vpu-imx8"
MACHINE_FIRMWARE:append:imx-mainline-bsp   = " linux-firmware-imx-sdma-imx6q linux-firmware-imx-sdma-imx7d firmware-imx-vpu-imx6q firmware-imx-vpu-imx6d"

MACHINE_EXTRA_RRECOMMENDS += "${MACHINE_FIRMWARE}"

# Extra audio support
IMX_ALSA_EXTRA = ""
IMX_ALSA_EXTRA:imx-nxp-bsp = "imx-alsa-plugins"
MACHINE_EXTRA_RRECOMMENDS:append:mx6-nxp-bsp = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', '${IMX_ALSA_EXTRA}', '', d)}"
MACHINE_EXTRA_RRECOMMENDS:append:mx7-nxp-bsp = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', '${IMX_ALSA_EXTRA}', '', d)}"
MACHINE_EXTRA_RRECOMMENDS:append:mx8-nxp-bsp = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', '${IMX_ALSA_EXTRA}', '', d)}"

# Extra Cypress Wi-Fi and BTW firmware (Murata)
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm4339', 'linux-firmware-bcm4339', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm43430', 'linux-firmware-bcm43430', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm43455', 'linux-firmware-bcm43455', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm4356', 'linux-firmware-bcm4356-pcie', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm4359', 'linux-firmware-bcm4359-pcie', '', d)}"

#Extra NXP89xx Wi-Fi and Bluetooth driver
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'nxp8987', 'kernel-module-nxp89xx', '', d)}"

# Extra QCA Wi-Fi & BTE driver and firmware
MACHINE_EXTRA_RRECOMMENDS:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'qca6174', 'packagegroup-fsl-qca6174', '', d)}"
MACHINE_EXTRA_RRECOMMENDS:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'qca9377', 'packagegroup-fsl-qca9377', '', d)}"

# Extra udev rules
MACHINE_EXTRA_RRECOMMENDS += "udev-rules-imx"

# Jailhouse
MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.filter('COMBINED_FEATURES', 'jailhouse', d)} \
"

# GStreamer 1.0 plugins
MACHINE_GSTREAMER_1_0_PLUGIN                ?= ""
MACHINE_GSTREAMER_1_0_PLUGIN:mx6dl-nxp-bsp  ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6q-nxp-bsp   ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6sl-nxp-bsp  ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6sx-nxp-bsp  ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6ul-nxp-bsp  ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6ull-nxp-bsp ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx7d-nxp-bsp   ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8mm-nxp-bsp  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8mn-nxp-bsp  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8mp-nxp-bsp  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8mq-nxp-bsp  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8qm-nxp-bsp  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8qxp-nxp-bsp ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8dx-nxp-bsp  ?= "imx-gst1.0-plugin"

PREFERRED_VERSION_gstreamer1.0:mx8-nxp-bsp              ??= "1.20.0.imx"
PREFERRED_VERSION_gstreamer1.0-plugins-base:mx8-nxp-bsp ??= "1.20.0.imx"
PREFERRED_VERSION_gstreamer1.0-plugins-good:mx8-nxp-bsp ??= "1.20.0.imx"
PREFERRED_VERSION_gstreamer1.0-plugins-bad:mx8-nxp-bsp  ??= "1.20.0.imx"
PREFERRED_VERSION_ffmpeg:mx8-nxp-bsp                    ??= "4.4.1"

# Determines if the SoC has support for Vivante kernel driver
SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT        = "0"
SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT:imxgpu = "1"

# Handle Vivante kernel driver setting:
#   0 - machine does not have Vivante GPU driver support
#   1 - machine has Vivante GPU driver support
MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT ?= "${SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT}"

# Graphics libraries
PREFERRED_PROVIDER_virtual/egl      ?= "mesa"
PREFERRED_PROVIDER_virtual/libgl    ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles1 ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles2 ?= "mesa"

PREFERRED_PROVIDER_virtual/egl:imxgpu        ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgl:imxgpu3d    ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles1:imxgpu3d ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles2:imxgpu3d ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles3:imxgpu3d ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libg2d            ?= "imx-gpu-g2d"
PREFERRED_PROVIDER_virtual/libg2d:imxdpu     ?= "imx-dpu-g2d"
PREFERRED_PROVIDER_opencl-clhpp:imxgpu       ?= "imx-gpu-viv"
PREFERRED_PROVIDER_opencl-headers:imxgpu     ?= "imx-gpu-viv"
PREFERRED_PROVIDER_opencl-icd-loader:imxgpu  ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/opencl-icd:imxgpu ?= "imx-gpu-viv"

PREFERRED_VERSION_weston:imx-nxp-bsp      ??= "10.0.0.imx"
PREFERRED_VERSION_weston:imx-mainline-bsp   = ""

PREFERRED_VERSION_wayland-protocols:mx6-nxp-bsp ??= "1.25.imx"
PREFERRED_VERSION_wayland-protocols:mx7-nxp-bsp ??= "1.25.imx"
PREFERRED_VERSION_wayland-protocols:mx8-nxp-bsp ??= "1.25.imx"

# Use i.MX libdrm Version
PREFERRED_VERSION_libdrm:mx6-nxp-bsp ??= "2.4.109.imx"
PREFERRED_VERSION_libdrm:mx7-nxp-bsp ??= "2.4.109.imx"
PREFERRED_VERSION_libdrm:mx8-nxp-bsp ??= "2.4.109.imx"

PREFERRED_VERSION_vulkan-headers:imxvulkan ??= "1.2.182.0"
PREFERRED_VERSION_vulkan-loader:imxvulkan  ??= "1.2.182.0"
PREFERRED_VERSION_vulkan-tools:imxvulkan   ??= "1.2.182.0"

# Use i.MX optee Version
PREFERRED_VERSION_optee-os:mx8-nxp-bsp     ??= "3.15.0.imx"
PREFERRED_VERSION_optee-client:mx8-nxp-bsp ??= "3.15.0.imx"
PREFERRED_VERSION_optee-test:mx8-nxp-bsp   ??= "3.15.0.imx"

#Use i.MX opencv Version for mx8
PREFERRED_VERSION_opencv:mx8-nxp-bsp ??= "4.5.2.imx"

# Handle default kernel
IMX_DEFAULT_KERNEL:imx-mainline-bsp = "linux-fslc"
IMX_DEFAULT_KERNEL:imx-nxp-bsp      = "linux-fslc-imx"

PREFERRED_PROVIDER_virtual/kernel ??= "${IMX_DEFAULT_KERNEL}"

SOC_DEFAULT_IMAGE_FSTYPES = "wic.bmap wic.gz"
SOC_DEFAULT_IMAGE_FSTYPES:mxs-generic-bsp = "uboot-mxsboot-sdcard wic.bmap wic.gz"

# Do not update fstab file when using wic images
WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

OPTEE_BIN_EXT ??= ""
OPTEE_BOOT_IMAGE         = "tee.bin uTee-${OPTEE_BIN_EXT}"
OPTEE_BOOT_IMAGE:aarch64 = "tee.bin"

IMAGE_FSTYPES ?= "${SOC_DEFAULT_IMAGE_FSTYPES}"

IMAGE_BOOT_FILES ?= " \
    ${KERNEL_IMAGETYPE} \
    ${@make_dtb_boot_files(d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'optee', '${OPTEE_BOOT_IMAGE}', '', d)} \
"

### wic default support
OPTEE_WKS_FILE_DEPENDS         = "optee-os"
OPTEE_WKS_FILE_DEPENDS:aarch64 = ""

WKS_FILE_DEPENDS ?= " \
    virtual/bootloader \
    \
    e2fsprogs-native \
    bmap-tools-native \
    ${@bb.utils.contains('MACHINE_FEATURES', 'optee', '${OPTEE_WKS_FILE_DEPENDS}', '', d)} \
"

# We need to restrict the append so we don't add this for other i.MX SoC's.
# Derivatives that are not yet adopted the usage of boot container provided
# by U-Boot build are still targeted to use 'imx-boot' package provided by
# NXP.
#
# Moving those derivatives to mainline BSP would require to define an
# 'imx-boot-container' override, and test if the U-Boot built 'flash.bin'
# binary is used a replacement.
#
# NOTE: the results binary name of the boot container is set to 'imx-boot'
# for both NXP and Mainline BSP.
WKS_FILE_DEPENDS:append:imx-generic-bsp:aarch64 = " \
    ${@oe.utils.ifelse(d.getVar('UBOOT_PROVIDES_BOOT_CONTAINER') == '0', 'imx-boot', '')} \
"

SOC_DEFAULT_WKS_FILE ?= "imx-uboot-bootpart.wks.in"
SOC_DEFAULT_WKS_FILE:mx8m-nxp-bsp ?= "imx-imx-boot-bootpart.wks.in"

SOC_DEFAULT_WKS_FILE:mx8-nxp-bsp ?= "imx-imx-boot-bootpart.wks.in"
SOC_DEFAULT_WKS_FILE:mxs-generic-bsp ?= "imx-uboot-mxs-bootpart.wks.in"

WKS_FILE ?= "${SOC_DEFAULT_WKS_FILE}"

SERIAL_CONSOLES = "115200;ttymxc0"
SERIAL_CONSOLES:mxs-generic-bsp = "115200;ttyAMA0"

KERNEL_IMAGETYPE = "zImage"
KERNEL_IMAGETYPE:aarch64 = "Image"

MACHINE_FEATURES = "usbgadget usbhost vfat alsa touchscreen"

HOSTTOOLS_NONFATAL:append:mx8-nxp-bsp = " sha384sum"

# Allow meta-imx to add NIP devices information until upstreamed.
include conf/machine/include/imx-base-extend.inc
