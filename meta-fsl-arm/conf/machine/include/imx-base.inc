# Provides the i.MX common settings

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

EXTRA_IMAGEDEPENDS = "u-boot"

IMAGE_FSTYPES ?= "tar.bz2 jffs2"
EXTRA_IMAGECMD_jffs2 = "-lnp "

SERIAL_CONSOLE = "115200 ttymxc0"

KERNEL_IMAGETYPE = "uImage"

MACHINE_FEATURES = "apm usbgadget usbhost vfat alsa touchscreen"
