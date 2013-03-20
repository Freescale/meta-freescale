# Provides the i.MX common settings

include conf/machine/include/fsl-default-settings.inc
include conf/machine/include/fsl-default-versions.inc
include conf/machine/include/fsl-default-providers.inc

include conf/machine/include/soc-family.inc

# Disable perf features as 2.6.35.3 fail to build otherwise
PERF_FEATURES_ENABLE = ""

# Set specific make target and binary suffix
UBOOT_MAKE_TARGET = "u-boot.imx"
UBOOT_SUFFIX ?= "imx"

UBOOT_ENTRYPOINT_mx51  = "0xa0000000"
UBOOT_LOADADDRESS_mx51 = "0xa0000000"

UBOOT_ENTRYPOINT_mx53  = "0x70800000"
UBOOT_LOADADDRESS_mx53 = "0x70800000"

UBOOT_ENTRYPOINT_mx6  = "0x10800000"
UBOOT_LOADADDRESS_mx6 = "0x10800000"

UBOOT_ENTRYPOINT_mx6sl = "0X80800000"
UBOOT_LOADADDRESS_mx6sl = "0X80800000"

XSERVER_DRIVER = "xf86-video-fbdev"
XSERVER_DRIVER_mx5 = "xf86-video-imxfb"
XSERVER_DRIVER_mx6 = "xf86-video-imxfb-vivante"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           ${XSERVER_DRIVER}"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

# Firmware
MACHINE_FIRMWARE ?= ""
MACHINE_FIRMWARE_mx6q = "firmware-imx-vpu-imx6q"
MACHINE_FIRMWARE_mx6d = "firmware-imx-vpu-imx6d"
MACHINE_FIRMWARE_mx6dl = "firmware-imx-vpu-imx6d"
MACHINE_FIRMWARE_mx53 = "firmware-imx-vpu-imx53 firmware-imx-sdma-imx53"
MACHINE_FIRMWARE_mx51 = "firmware-imx-vpu-imx51 firmware-imx-sdma-imx51"
MACHINE_FIRMWARE_mx35 = "firmware-imx-sdma-imx35"
MACHINE_FIRMWARE_mx31 = "firmware-imx-sdma-imx31"
MACHINE_FIRMWARE_mx27 = "firmware-imx-vpu-imx27"
MACHINE_FIRMWARE_mx25 = "firmware-imx-sdma-imx25"

MACHINE_EXTRA_RRECOMMENDS += "${MACHINE_FIRMWARE}"

# Extra audio support
MACHINE_EXTRA_RRECOMMENDS += "imx-audio"

# Extra udev rules
MACHINE_EXTRA_RRECOMMENDS += "udev-extraconf"

# Gstreamer plugin
MACHINE_GSTREAMER_PLUGIN ?= "gst-fsl-plugin"

# mx5 GPU libraries
PREFERRED_PROVIDER_virtual/egl_mx5 ?= "amd-gpu-x11-bin-mx51"
PREFERRED_PROVIDER_virtual/libgles1_mx5 ?= "amd-gpu-x11-bin-mx51"
PREFERRED_PROVIDER_virtual/libgles2_mx5 ?= "amd-gpu-x11-bin-mx51"

# mx6 GPU libraries
PREFERRED_PROVIDER_virtual/egl_mx6 ?= "gpu-viv-bin-mx6q"
PREFERRED_PROVIDER_virtual/libgl_mx6 ?= "gpu-viv-bin-mx6q"
PREFERRED_PROVIDER_virtual/libgles1_mx6 ?= "gpu-viv-bin-mx6q"
PREFERRED_PROVIDER_virtual/libgles2_mx6 ?= "gpu-viv-bin-mx6q"

PREFERRED_PROVIDER_virtual/kernel_mx3 = "linux-fslc"
PREFERRED_PROVIDER_u-boot_mx6sl = "u-boot-imx"
PREFERRED_PROVIDER_u-boot_mx6dl = "u-boot-imx"

EXTRA_IMAGEDEPENDS = "u-boot"

SDCARD_ROOTFS ?= "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext3"
IMAGE_FSTYPES ?= "tar.bz2 ext3 sdcard"

SERIAL_CONSOLE = "115200 ttymxc0"

KERNEL_IMAGETYPE = "uImage"

MACHINE_FEATURES = "apm usbgadget usbhost vfat alsa touchscreen"
