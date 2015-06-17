# Provides the i.MXS common settings

include conf/machine/include/fsl-default-settings.inc
include conf/machine/include/fsl-default-versions.inc

include conf/machine/include/soc-family.inc
include conf/machine/include/tune-arm926ejs.inc

SOC_FAMILY = "mxs"

PREFERRED_PROVIDER_virtual/kernel ??= "linux-imx"
PREFERRED_PROVIDER_virtual/kernel_mx23 ?= "linux-fslc"

PREFERRED_PROVIDER_u-boot ??= "u-boot-fslc"
PREFERRED_PROVIDER_virtual/bootloader ??= "u-boot-fslc"
UBOOT_ENTRYPOINT = "0x40008000"
UBOOT_MAKE_TARGET = "u-boot.sb"
UBOOT_SUFFIX = "sb"

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xf86-video-fbdev"

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""

MACHINE_ARCH_FILTER = "virtual/kernel"

INHERIT += "fsl-dynamic-packagearch"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

SERIAL_CONSOLE = "115200 ttyAMA0"

KERNEL_IMAGETYPE = "zImage"
