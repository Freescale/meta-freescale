# Provides the i.MX53 common settings

include conf/machine/include/tune-cortexa8.inc

include conf/distro/include/fsl-default-versions.inc
include conf/distro/include/fsl-default-revisions.inc
include conf/distro/include/fsl-default-providers.inc

# Todo:
# 	Check Freescale x11 framebuffer
#	Check jffs2 extra commands

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg-lite"
PREFERRED_PROVIDER_virtual/xserver-xf86 = "xserver-xorg-lite"
XSERVER = "xserver-xorg-lite \
           xf86-input-evdev \
           xf86-input-keyboard \
           xf86-input-mouse \
           xf86-video-imxfb"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

# Extra audio support
MACHINE_EXTRA_RRECOMMENDS += "imx-lib imx-audio \
                              pulseaudio \
                              fsl-mm-codeclib \
                              gst-fsl-plugin"

EXTRA_IMAGE_DEPENDS = "u-boot-imx"

IMAGE_FSTYPES ?= "tar.bz2 jffs2"
EXTRA_IMAGECMD_jffs2 = "-lnp "

SERIAL_CONSOLE = "115200 ttymxc0"

KERNEL_IMAGETYPE = "uImage"

UBOOT_ENTRYPOINT = "0x70800000"
UBOOT_LOADADDRESS = "0x70800000"

MACHINE_FEATURES = "kernel26 apm usbgadget usbhost vfat alsa touchscreen"
