# Provides the i.MX common settings

include conf/distro/include/fsl-default-settings.inc
include conf/distro/include/fsl-default-versions.inc
include conf/distro/include/fsl-default-providers.inc

# Disable perf features as 2.6.35.3 fail to build otherwise
PERF_FEATURES_ENABLE = ""

# Set specific make target and binary suffix
UBOOT_MAKE_TARGET = "u-boot.imx"
UBOOT_SUFFIX = "imx"

XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xf86-video-imxfb"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

# Extra audio support
MACHINE_EXTRA_RRECOMMENDS += "imx-audio \
                              imx-firmware \
                              imx-lib \
                              udev-extraconf \
                             "

# Gstreamer plugin
MACHINE_GSTREAMER_PLUGIN ?= "gst-fsl-plugin"

PREFERRED_PROVIDER_virtual/libgl_mx5 ?= "amd-gpu-x11-bin-mx51"

EXTRA_IMAGEDEPENDS = "u-boot"

SDCARD_ROOTFS ?= "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext3"
IMAGE_FSTYPES ?= "tar.bz2 ext3 sdcard"

SERIAL_CONSOLE = "115200 ttymxc0"

KERNEL_IMAGETYPE = "uImage"

MACHINE_FEATURES = "apm usbgadget usbhost vfat alsa touchscreen"
