# Provides the i.MX common settings

include conf/machine/include/fsl-default-settings.inc
include conf/machine/include/fsl-default-versions.inc

# Set specific make target and binary suffix
PREFERRED_PROVIDER_u-boot ??= "u-boot-fslc"
PREFERRED_PROVIDER_virtual/bootloader ??= "u-boot-fslc"

PREFERRED_PROVIDER_u-boot-mxsboot-native ??= "u-boot-fslc-mxsboot-native"

UBOOT_MAKE_TARGET ?= "u-boot.imx"
UBOOT_MAKE_TARGET_mxs ?= "u-boot.sb"

UBOOT_SUFFIX ?= "imx"
UBOOT_SUFFIX_mxs ?= "sb"

UBOOT_ENTRYPOINT_mxs = "0x40008000"
UBOOT_ENTRYPOINT_mx51  = "0x90008000"
UBOOT_ENTRYPOINT_mx53  = "0x70008000"
UBOOT_ENTRYPOINT_mx6  = "0x10008000"
UBOOT_ENTRYPOINT_mx6sl = "0x80008000"
UBOOT_ENTRYPOINT_mx6sx = "0x80008000"
UBOOT_ENTRYPOINT_mx6ul  = "0x10008000"
UBOOT_ENTRYPOINT_mx6ull  = "0x10008000"
UBOOT_ENTRYPOINT_mx7 = "0x80008000"
UBOOT_ENTRYPOINT_vf = "0x80008000"

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
XSERVER_DRIVER = "xf86-video-fbdev"
XSERVER_DRIVER_imxgpu2d = "xf86-video-imxfb-vivante"
XSERVER_DRIVER_vf = "xf86-video-modesetting"
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
# DEFAULTTUNE_mx6 ?= "cortexa9t-neon"
# handled by hardware
DEFAULTTUNE_mx6 ?= "cortexa9thf-neon"
DEFAULTTUNE_mx6ul ?= "cortexa7thf-neon"
DEFAULTTUNE_mx6ull ?= "cortexa7thf-neon"
DEFAULTTUNE_mx7 ?= "cortexa7thf-neon"
DEFAULTTUNE_vf ?= "cortexa5thf-neon"

INHERIT += "machine-overrides-extender"

MACHINEOVERRIDES_EXTENDER_mx6q  = "imxipu:imxvpu:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER_mx6dl = "imxpxp:imxipu:imxvpu:imxgpu2d:imxgpu3d:imxepdc"
MACHINEOVERRIDES_EXTENDER_mx6sx = "imxpxp:imxgpu2d:imxgpu3d"
MACHINEOVERRIDES_EXTENDER_mx6sl = "imxpxp:imxgpu2d:imxepdc"
MACHINEOVERRIDES_EXTENDER_mx6ul = "imxpxp"
MACHINEOVERRIDES_EXTENDER_mx6ull = "imxpxp:imxepdc"
MACHINEOVERRIDES_EXTENDER_mx7d  = "imxpxp:imxepdc"

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""
MACHINE_SOCARCH_SUFFIX_mx6q = "-mx6qdl"
MACHINE_SOCARCH_SUFFIX_mx6dl = "-mx6qdl"
MACHINE_SOCARCH_SUFFIX_mx6sx = "-mx6sx"
MACHINE_SOCARCH_SUFFIX_mx6sl = "-mx6sl"
MACHINE_SOCARCH_SUFFIX_mx7d = "-mx7d"
MACHINE_SOCARCH_SUFFIX_vf60 = "-vf60"
MACHINE_SOCARCH_SUFFIX_vf50 = "-vf50"
MACHINE_SOCARCH_SUFFIX_mx6ul  = "-mx6ul"
MACHINE_SOCARCH_SUFFIX_mx6ull = "-mx6ul"

MACHINE_ARCH_FILTER = "virtual/kernel"
MACHINE_SOCARCH_FILTER_append_imxvpu = " imx-vpu libimxvpuapi imx-codec imx-vpuwrap imx-parser"
MACHINE_SOCARCH_FILTER_append_imxgpu2d = " virtual/libopenvg virtual/libgles1 virtual/libgles2 virtual/egl virtual/mesa virtual/libgl virtual/libg2d cairo pango"
MACHINE_SOCARCH_FILTER_append_imxpxp = " imx-codec imx-parser"

INHERIT += "fsl-dynamic-packagearch"

SIGGEN_EXCLUDE_SAFE_RECIPE_DEPS_append = " \
    imx-gpu-viv->kernel-module-imx-gpu-viv \
    libimxvpuapi->imx-vpu \
    imx-vpuwrap->imx-vpu \
    imx-codec->imx-vpu \
    imx-test->imx-vpu \
"

# Firmware
MACHINE_FIRMWARE ?= ""
MACHINE_FIRMWARE_append_mx7 = " firmware-imx-epdc"
MACHINE_FIRMWARE_append_mx6q = " firmware-imx-vpu-imx6q"
MACHINE_FIRMWARE_append_mx6dl = " firmware-imx-vpu-imx6d firmware-imx-epdc"
MACHINE_FIRMWARE_append_mx6sl = " firmware-imx-epdc"
MACHINE_FIRMWARE_append_mx6ull = " firmware-imx-epdc"
MACHINE_FIRMWARE_append_mx53 = " firmware-imx-vpu-imx53 firmware-imx-sdma-imx53"
MACHINE_FIRMWARE_append_mx51 = " firmware-imx-vpu-imx51 firmware-imx-sdma-imx51"

# FIXME: Needs addition of firmware-imx of official BSPs
#MACHINE_FIRMWARE_append_mx27 = " firmware-imx-vpu-imx27"
#MACHINE_FIRMWARE_append_mx25 = " firmware-imx-sdma-imx25"

MACHINE_EXTRA_RRECOMMENDS += "${MACHINE_FIRMWARE}"

# Extra audio support
# FIXME: Add support for ALL SoC families
MACHINE_EXTRA_RRECOMMENDS_append_mx6dl = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'imx-alsa-plugins', '', d)}"
MACHINE_EXTRA_RRECOMMENDS_append_mx6q = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'imx-alsa-plugins', '', d)}"
MACHINE_EXTRA_RRECOMMENDS_append_mx6sl = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'imx-alsa-plugins', '', d)}"
MACHINE_EXTRA_RRECOMMENDS_append_mx6sx = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'imx-alsa-plugins', '', d)}"
MACHINE_EXTRA_RRECOMMENDS_append_mx6ul = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'imx-alsa-plugins', '', d)}"
MACHINE_EXTRA_RRECOMMENDS_append_mx6ull = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'imx-alsa-plugins', '', d)}"
MACHINE_EXTRA_RRECOMMENDS_append_mx7d = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'imx-alsa-plugins', '', d)}"

# Extra udev rules
MACHINE_EXTRA_RRECOMMENDS += "udev-rules-imx"

# GStreamer 1.0 plugins
MACHINE_GSTREAMER_1_0_PLUGIN ?= ""
MACHINE_GSTREAMER_1_0_PLUGIN_mx6dl ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN_mx6q ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN_mx6sl ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN_mx6sx ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN_mx6ul ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN_mx6ull ?= "gstreamer1.0-plugins-imx-meta"
MACHINE_GSTREAMER_1_0_PLUGIN_mx7d ?= "gstreamer1.0-plugins-imx-meta"

# Determines if the SoC has support for Vivante kernel driver
SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT       = "0"
SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT_mx6q  = "1"
SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT_mx6dl = "1"
SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT_mx6sx = "1"
SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT_mx6sl = "1"

# Handle Vivante kernel driver setting:
#   0 - machine does not have Vivante GPU driver support
#   1 - machine has Vivante GPU driver support
MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT ?= "${SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT}"

# mx6 GPU libraries
PREFERRED_PROVIDER_virtual/egl_mx6q ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/egl_mx6dl ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/egl_mx6sx ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/egl_mx6sl ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/egl_mx6ul ?= "mesa"
PREFERRED_PROVIDER_virtual/egl_mx6ull ?= "mesa"

PREFERRED_PROVIDER_virtual/libgles1_mx6q ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles1_mx6dl ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles1_mx6sx ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles1_mx6sl ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles1_mx6ul ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles1_mx6ull ?= "mesa"

PREFERRED_PROVIDER_virtual/libgles2_mx6q ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles2_mx6dl ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles2_mx6sx ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgles2_mx6sl ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles2_mx6ul ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles2_mx6ull ?= "mesa"

PREFERRED_PROVIDER_virtual/libgl_mx6q ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgl_mx6dl ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgl_mx6sx ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libgl_mx6sl ?= "mesa"
PREFERRED_PROVIDER_virtual/libgl_mx6ul ?= "mesa"
PREFERRED_PROVIDER_virtual/libgl_mx6ull ?= "mesa"

PREFERRED_PROVIDER_virtual/libg2d_mx6 ?= "imx-gpu-viv"
PREFERRED_PROVIDER_virtual/libg2d_mx6ul = ""
PREFERRED_PROVIDER_virtual/libg2d_mx6ull = ""

# Handle default kernel
IMX_DEFAULT_KERNEL = "linux-imx"
IMX_DEFAULT_KERNEL_mxs = "linux-fslc"
IMX_DEFAULT_KERNEL_mx5 = "linux-fslc"
IMX_DEFAULT_KERNEL_mx6 = "linux-fslc-imx"
IMX_DEFAULT_KERNEL_mx7 = "linux-fslc-imx"
IMX_DEFAULT_KERNEL_mx6ul = "linux-fslc-imx"
IMX_DEFAULT_KERNEL_mx6ull = "linux-fslc-imx"

PREFERRED_PROVIDER_virtual/kernel ??= "${IMX_DEFAULT_KERNEL}"

SOC_DEFAULT_IMAGE_FSTYPES = "sdcard.gz"
SOC_DEFAULT_IMAGE_FSTYPES_mxs = "uboot-mxsboot-sdcard sdcard.gz"

SDCARD_ROOTFS ?= "${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4"
IMAGE_FSTYPES ?= "${SOC_DEFAULT_IMAGE_FSTYPES}"

SERIAL_CONSOLE = "115200 ttymxc0"
SERIAL_CONSOLE_mxs = "115200 ttyAMA0"

KERNEL_IMAGETYPE = "zImage"

MACHINE_FEATURES = "usbgadget usbhost vfat alsa touchscreen"

# Add the ability to specify _imx machines
MACHINEOVERRIDES =. "imx:"
