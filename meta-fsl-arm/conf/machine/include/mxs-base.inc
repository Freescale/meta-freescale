# Provides the i.MXS common settings

include conf/machine/include/fsl-default-settings.inc
include conf/machine/include/fsl-default-versions.inc
include conf/machine/include/fsl-default-providers.inc

include conf/machine/include/soc-family.inc
include conf/machine/include/tune-arm926ejs.inc

SOC_FAMILY = "mxs"

PREFERRED_PROVIDER_virtual/kernel_mx23 = "linux-fslc"

UBOOT_ENTRYPOINT = "0x40008000"
UBOOT_MAKE_TARGET = "u-boot.sb"
UBOOT_SUFFIX = "sb"

# Disable perf features as 2.6.35.3 fail to build otherwise
PERF_FEATURES_ENABLE_mx28 = ""

XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xf86-video-fbdev"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

SERIAL_CONSOLE = "115200 ttyAMA0"
