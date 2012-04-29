# Provides the i.MX common settings

include conf/distro/include/fsl-default-settings.inc
include conf/distro/include/fsl-default-versions.inc
include conf/distro/include/fsl-default-revisions.inc
include conf/distro/include/fsl-default-providers.inc

# Set specific make target and binary suffix
UBOOT_MAKE_TARGET_mx5 = "u-boot.imx"
UBOOT_SUFFIX_mx5 = "imx"
UBOOT_MAKE_TARGET_mx6 = "u-boot.imx"
UBOOT_SUFFIX_mx6 = "imx"

XSERVER = "xserver-xorg-lite \
           xf86-input-evdev \
           xf86-video-imxfb"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

# Extra audio support
MACHINE_EXTRA_RRECOMMENDS += "imx-lib imx-audio \
                              pulseaudio \
                              fsl-mm-codeclib \
                              gst-fsl-plugin"

EXTRA_IMAGEDEPENDS = "u-boot"

SDCARD_ROOTFS ?= "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext3"
IMAGE_FSTYPES ?= "tar.bz2 ext3 sdcard"

SERIAL_CONSOLE = "115200 ttymxc0"

KERNEL_IMAGETYPE = "uImage"

MACHINE_FEATURES = "apm usbgadget usbhost vfat alsa touchscreen"
