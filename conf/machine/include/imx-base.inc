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

UBOOT_ENTRYPOINT:mxs    = "0x40008000"
UBOOT_ENTRYPOINT:mx51   = "0x90008000"
UBOOT_ENTRYPOINT:mx53   = "0x70008000"
UBOOT_ENTRYPOINT:mx6    = "0x10008000"
UBOOT_ENTRYPOINT:mx6sl  = "0x80008000"
UBOOT_ENTRYPOINT:mx6sll = "0x80008000"
UBOOT_ENTRYPOINT:mx6sx  = "0x80008000"
UBOOT_ENTRYPOINT:mx6ul  = "0x80008000"
UBOOT_ENTRYPOINT:mx6ull = "0x80008000"
UBOOT_ENTRYPOINT:mx6ulz = "0x80008000"
UBOOT_ENTRYPOINT:mx7    = "0x80008000"
UBOOT_ENTRYPOINT:mx7ulp = "0x60008000"
UBOOT_ENTRYPOINT:mx8m   = "0x40480000"
UBOOT_ENTRYPOINT:vf = "0x80008000"

# Some derivates can utilize the boot container provided by U-Boot,
# below variable sets that those machines which have a imx-boot-container
# in their MACHINEOVERRIDES can inherit a imx-boot-container class
UBOOT_PROVIDES_BOOT_CONTAINER = "0"
UBOOT_PROVIDES_BOOT_CONTAINER:imx-boot-container = "1"

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
XSERVER_DRIVER                  = "xf86-video-fbdev"
XSERVER_DRIVER:imxgpu2d         = "xf86-video-imx-vivante"
XSERVER_DRIVER:vf               = "xf86-video-modesetting"
XSERVER_DRIVER:append:mx8       = " xf86-video-modesetting"
XSERVER_DRIVER:use-mainline-bsp = " \
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
# DEFAULTTUNE:mx6 ?= "cortexa9t-neon"
# handled by hardware
DEFAULTTUNE:mx6    ?= "cortexa9thf-neon"
DEFAULTTUNE:mx6ul  ?= "cortexa7thf-neon"
DEFAULTTUNE:mx6ull ?= "cortexa7thf-neon"
DEFAULTTUNE:mx6ulz ?= "cortexa7thf-neon"
DEFAULTTUNE:mx7    ?= "cortexa7thf-neon"
DEFAULTTUNE:vf     ?= "cortexa5thf-neon"

DEFAULTTUNE:mx8m   ?= "cortexa53-crypto"
DEFAULTTUNE:mx8qm  ?= "cortexa72-cortexa53-crypto"
DEFAULTTUNE:mx8qxp ?= "cortexa35-crypto"

INHERIT += "machine-overrides-extender"

MACHINEOVERRIDES_EXTENDER:mx25   = "use-mainline-bsp"
MACHINEOVERRIDES_EXTENDER:mx6q   = "imxfbdev:imxipu:imxvpu:imxgpu:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx6dl  = "imxfbdev:imxpxp:imxipu:imxvpu:imxgpu:imxgpu2d:imxgpu3d:imxepdc"
MACHINEOVERRIDES_EXTENDER:mx6sx  = "imxfbdev:imxpxp:imxgpu:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx6sl  = "imxfbdev:imxpxp:imxgpu:imxgpu2d:imxepdc"
MACHINEOVERRIDES_EXTENDER:mx6sll = "imxfbdev:imxpxp:imxepdc"
MACHINEOVERRIDES_EXTENDER:mx6ul  = "imxfbdev:imxpxp"
MACHINEOVERRIDES_EXTENDER:mx6ull = "imxfbdev:imxpxp:imxepdc"
MACHINEOVERRIDES_EXTENDER:mx6ulz = "imxfbdev:imxpxp:imxepdc"
MACHINEOVERRIDES_EXTENDER:mx7d   = "imxfbdev:imxpxp:imxepdc"
MACHINEOVERRIDES_EXTENDER:mx7ulp = "imxfbdev:imxpxp:imxgpu:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx8qm  = "imxdrm:imxdpu:imxgpu:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx8mm  = "imxdrm:imxvpu:imxgpu:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx8mn  = "imxdrm:imxgpu:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx8mp  = "imxdrm:imxvpu:imxgpu:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx8mq  = "imxdrm:imxvpu:imxgpu:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx8qxp = "imxdrm:imxdpu:imxgpu:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER:mx8dxl = "imxfbdev"

MACHINEOVERRIDES_EXTENDER_FILTER_OUT:use-mainline-bsp = " \
    imx \
    \
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
    mx8 \
    mx8m \
    mx8qm \
    mx8mm \
    mx8mn \
    mx8mp \
    mx8mq \
    mx8qxp \
    mx8dxl \
"

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""
MACHINE_SOCARCH_SUFFIX:mx6q = "-mx6qdl"
MACHINE_SOCARCH_SUFFIX:mx6dl = "-mx6qdl"
MACHINE_SOCARCH_SUFFIX:mx6sx = "-mx6sx"
MACHINE_SOCARCH_SUFFIX:mx6sl = "-mx6sl"
MACHINE_SOCARCH_SUFFIX:mx6sll= "-mx6sll"
MACHINE_SOCARCH_SUFFIX:mx7d = "-mx7d"
MACHINE_SOCARCH_SUFFIX:mx7ulp = "-mx7ulp"
MACHINE_SOCARCH_SUFFIX:vf60 = "-vf60"
MACHINE_SOCARCH_SUFFIX:vf50 = "-vf50"
MACHINE_SOCARCH_SUFFIX:mx6ul  = "-mx6ul"
MACHINE_SOCARCH_SUFFIX:mx6ull = "-mx6ul"
MACHINE_SOCARCH_SUFFIX:mx6ulz = "-mx6ul"
MACHINE_SOCARCH_SUFFIX:mx8qm  = "-mx8"
MACHINE_SOCARCH_SUFFIX:mx8mm  = "-mx8mm"
MACHINE_SOCARCH_SUFFIX:mx8mn  = "-mx8mn"
MACHINE_SOCARCH_SUFFIX:mx8mp  = "-mx8mp"
MACHINE_SOCARCH_SUFFIX:mx8mq  = "-mx8m"
MACHINE_SOCARCH_SUFFIX:mx8qxp = "-mx8"
MACHINE_SOCARCH_SUFFIX:mx8dxl = "-mx8dxl"
MACHINE_SOCARCH_SUFFIX:use-mainline-bsp = "-imx"

MACHINE_ARCH_FILTER = "virtual/kernel"
MACHINE_SOCARCH_FILTER:append:imx = " \
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
MACHINE_SOCARCH_FILTER:append:use-mainline-bsp = " \
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
MACHINE_SOCARCH_FILTER:append:mx6q = " \
    opencl-icd-loader \
    opencl-clhpp \
    opencl-headers \
"
MACHINE_SOCARCH_FILTER:append:mx8 = " \
    opencl-icd-loader \
    opencl-clhpp \
    opencl-headers \
"
MACHINE_SOCARCH_FILTER:append:mx8qm = " \
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

# Firmware
MACHINE_FIRMWARE ?= ""
MACHINE_FIRMWARE:append:mx25 = " firmware-imx-sdma-imx25"
MACHINE_FIRMWARE:append:mx27 = " firmware-imx-vpu-imx27"
MACHINE_FIRMWARE:append:mx7d = " linux-firmware-imx-sdma-imx7d firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx6 = " linux-firmware-imx-sdma-imx6q"
MACHINE_FIRMWARE:append:mx6q = " firmware-imx-vpu-imx6q"
MACHINE_FIRMWARE:append:mx6dl = " firmware-imx-vpu-imx6d firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx6sl = " firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx6sll = " firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx6ull = " firmware-imx-epdc"
MACHINE_FIRMWARE:append:mx53 = " firmware-imx-vpu-imx53 firmware-imx-sdma-imx53"
MACHINE_FIRMWARE:append:mx51 = " firmware-imx-vpu-imx51 firmware-imx-sdma-imx51"
MACHINE_FIRMWARE:append:mx8mm  = " linux-firmware-imx-sdma-imx7d"
MACHINE_FIRMWARE:append:mx8mn  = " linux-firmware-imx-sdma-imx7d"
MACHINE_FIRMWARE:append:mx8mp  = " linux-firmware-imx-sdma-imx7d firmware-imx-easrc-imx8mn firmware-imx-xcvr-imx8mp firmware-sof-imx"
MACHINE_FIRMWARE:append:mx8mq  = " linux-firmware-imx-sdma-imx7d"
MACHINE_FIRMWARE:append:mx8qm  = " firmware-imx-vpu-imx8"
MACHINE_FIRMWARE:append:mx8qxp = " firmware-imx-vpu-imx8"
MACHINE_FIRMWARE:append:use-mainline-bsp = " linux-firmware-imx-sdma-imx6q linux-firmware-imx-sdma-imx7d firmware-imx-vpu-imx6q firmware-imx-vpu-imx6d"

MACHINE_EXTRA_RRECOMMENDS += "${MACHINE_FIRMWARE}"

# Extra audio support
IMX_ALSA_EXTRA = ""
IMX_ALSA_EXTRA:use-nxp-bsp = "imx-alsa-plugins"
MACHINE_EXTRA_RRECOMMENDS:append:mx6 = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', '${IMX_ALSA_EXTRA}', '', d)}"
MACHINE_EXTRA_RRECOMMENDS:append:mx7 = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', '${IMX_ALSA_EXTRA}', '', d)}"
MACHINE_EXTRA_RRECOMMENDS:append:mx8 = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', '${IMX_ALSA_EXTRA}', '', d)}"

# Extra Cypress Wi-Fi and BTW firmware (Murata)
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm4339', 'linux-firmware-bcm4339', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm43430', 'linux-firmware-bcm43430', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm43455', 'linux-firmware-bcm43455', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm4356', 'linux-firmware-bcm4356-pcie', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm4359', 'linux-firmware-bcm4359-pcie', '', d)}"

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
MACHINE_GSTREAMER_1_0_PLUGIN        ?= ""
MACHINE_GSTREAMER_1_0_PLUGIN:mx6dl  ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6q   ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6sl  ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6sx  ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6ul  ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx6ull ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx7d   ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8mm  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8mn  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8mp  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8mq  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8qm  ?= "imx-gst1.0-plugin"
MACHINE_GSTREAMER_1_0_PLUGIN:mx8qxp ?= "imx-gst1.0-plugin"

PREFERRED_VERSION_gstreamer1.0:mx8 ?= "1.16.2.imx"
PREFERRED_VERSION_gstreamer1.0-plugins-base:mx8 ?= "1.16.2.imx"
PREFERRED_VERSION_gstreamer1.0-plugins-good:mx8 ?= "1.16.3.imx"
PREFERRED_VERSION_gstreamer1.0-plugins-bad:mx8 ?= "1.16.3.imx"
PREFERRED_VERSION_gstreamer1.0-libav:mx8 ?= "1.16.0"

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
PREFERRED_PROVIDER_virtual/libg2d            ?= "imx-gpu-g2d"
PREFERRED_PROVIDER_virtual/libg2d:imxdpu     ?= "imx-dpu-g2d"
PREFERRED_PROVIDER_opencl-clhpp:imxgpu       ?= "imx-gpu-viv"
PREFERRED_PROVIDER_opencl-headers:imxgpu     ?= "imx-gpu-viv"
PREFERRED_PROVIDER_opencl-icd-loader:imxgpu  ?= "imx-gpu-viv"

PREFERRED_VERSION_weston:imx ?= "9.0.0.imx"
PREFERRED_VERSION_weston:use-mainline-bsp = ""

PREFERRED_VERSION_wayland-protocols:mx6 ?= "1.20.imx"
PREFERRED_VERSION_wayland-protocols:mx7 ?= "1.20.imx"
PREFERRED_VERSION_wayland-protocols:mx8 ?= "1.20.imx"

# Use i.MX libdrm Version
PREFERRED_VERSION_libdrm:mx6 ?= "2.4.102.imx"
PREFERRED_VERSION_libdrm:mx7 ?= "2.4.102.imx"
PREFERRED_VERSION_libdrm:mx8 ?= "2.4.102.imx"

# Use i.MX optee Version
PREFERRED_VERSION_optee-os:mx8     ?= "3.10.0.imx"
PREFERRED_VERSION_optee-client:mx8 ?= "3.10.0.imx"
PREFERRED_VERSION_optee-test:mx8   ?= "3.10.0.imx"

#Use i.MX opencv Version for mx8
PREFERRED_VERSION_opencv:mx8 ?= "4.5.2.imx"

# Handle default kernel
IMX_DEFAULT_KERNEL = "linux-fslc-imx"
IMX_DEFAULT_KERNEL:mxs = "linux-fslc"
IMX_DEFAULT_KERNEL:mx5 = "linux-fslc"
IMX_DEFAULT_KERNEL:mx6 = "linux-fslc-imx"
IMX_DEFAULT_KERNEL:mx7 = "linux-fslc-imx"
IMX_DEFAULT_KERNEL:mx8 = "linux-fslc-imx"
IMX_DEFAULT_KERNEL:mx7ulp = "linux-fslc-imx"
IMX_DEFAULT_KERNEL:mx6sll = "linux-fslc-imx"
IMX_DEFAULT_KERNEL:mx6ul = "linux-fslc-imx"
IMX_DEFAULT_KERNEL:mx6ull = "linux-fslc-imx"
IMX_DEFAULT_KERNEL:use-mainline-bsp = "linux-fslc"

PREFERRED_PROVIDER_virtual/kernel ??= "${IMX_DEFAULT_KERNEL}"

SOC_DEFAULT_IMAGE_FSTYPES = "wic.bmap wic.gz"
SOC_DEFAULT_IMAGE_FSTYPES:mxs = "uboot-mxsboot-sdcard wic.bmap wic.gz"

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

WKS_FILE_DEPENDS:append:mx8 = " imx-boot"
WKS_FILE_DEPENDS:append:mx8m = " imx-boot"

# We need to restrict the append so we don't add this for other i.MX SoC's.
# Derivatives that are not yet adopted the usage of boot container provided
# by U-Boot build are still targeted to use 'imx-boot' package provided by
# NXP. Moving those derivatives to mainline BSP would require to define an
# 'imx-boot-container' override, and test if the U-Boot built 'flash.bin'
# binary is used a replacement.
# Note, that the results binary name of the boot container is set to 'imx-boot'
# for both NXP and Mainline BSP.
# For Mainline BSP: the 'flash.bin' boot container is renamed during the
# deployment task extesion execution defined in imx-boot-container class.
# For NXP BSP: rename is done in 'imx-boot' recipe at the execution of compile
# task.
WKS_FILE_DEPENDS:append:use-mainline-bsp:aarch64 = " \
    ${@oe.utils.ifelse(d.getVar('UBOOT_PROVIDES_BOOT_CONTAINER') == '0', 'imx-boot', '')} \
"

SOC_DEFAULT_WKS_FILE ?= "imx-uboot-bootpart.wks.in"
SOC_DEFAULT_WKS_FILE:mx8m ?= "imx-imx-boot-bootpart.wks.in"

SOC_DEFAULT_WKS_FILE:mx8 ?= "imx-imx-boot-bootpart.wks.in"
SOC_DEFAULT_WKS_FILE:mxs ?= "imx-uboot-mxs-bootpart.wks.in"

# Boot container built as a part of mainline U-Boot uses different WKS
# file as the entire mx8m series, as U-Boot versions later than 2021.04 are
# providing two separate binaries, namely flash.bin and u-boot.itb. Those
# files are packed into the boot partition.
# Binaries produced by U-Boot build itself are serving as a direct
# replacement of imx-boot from NXP.
# Creation of those binary files is controlled by UBOOT_PROVIDES_BOOT_CONTAINER
# variable defined above.
SOC_DEFAULT_WKS_FILE:imx-boot-container ?= "imx-boot-container-bootpart.wks.in"

WKS_FILE ?= "${SOC_DEFAULT_WKS_FILE}"

SERIAL_CONSOLES = "115200;ttymxc0"
SERIAL_CONSOLES:mxs = "115200;ttyAMA0"

KERNEL_IMAGETYPE = "zImage"
KERNEL_IMAGETYPE:aarch64 = "Image"

MACHINE_FEATURES = "usbgadget usbhost vfat alsa touchscreen"

# Add the ability to specify imx machines
MACHINEOVERRIDES =. "imx:"

HOSTTOOLS_NONFATAL:append:mx8 = " sha384sum"
