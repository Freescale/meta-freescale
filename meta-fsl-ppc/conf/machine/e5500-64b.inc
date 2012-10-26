TARGET_FPU = "hard"

DEFAULTTUNE ?= "ppc64e5500"
require conf/machine/include/tune-ppce5500.inc

MACHINE_FEATURES = "kernel26 keyboard pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS = "u-boot"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq-sdk"
PREFERRED_VERSION_virtual/kernel ?= "3.0.34"
PREFERRED_PROVIDER_linux-libc-headers ?= "linux-qoriq-sdk-headers"
PREFERRED_VERSION_qemu = "1.0+fsl"
PREFERRED_VERSION_oprofile = "0.9.6"

KERNEL_IMAGETYPE ?= "uImage"
# disable the images below for now
#		ext2.bz2 ext2.lzma \
#		ext2.gz.u-boot ext2.bz2.u-boot ext2.lzma.u-boot"

MACHINEOVERRIDES .= ":e5500-64b:fslmachine"
