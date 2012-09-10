# Provides the i.MXS common settings

include conf/distro/include/fsl-default-settings.inc
include conf/distro/include/fsl-default-versions.inc
include conf/distro/include/fsl-default-providers.inc

include conf/machine/include/soc-family.inc
include conf/machine/include/tune-arm926ejs.inc

SOC_FAMILY = "mxs"

# Disable perf features as 2.6.35.3 fail to build otherwise
PERF_FEATURES_ENABLE = ""

XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xf86-video-fbdev"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

SERIAL_CONSOLE = "115200 ttyAMA0"
