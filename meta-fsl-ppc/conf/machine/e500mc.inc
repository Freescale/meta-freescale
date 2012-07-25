TARGET_FPU = "hard"

require conf/machine/include/tune-ppce500mc.inc

MACHINE_FEATURES = "kernel26 keyboard pci ext2 ext3 serial"
MACHINE_FEATURES_RRECOMMENDS = ""

PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq-sdk"
PREFERRED_VERSION_virtual/kernel ?= "3.0.34"
PREFERRED_PROVIDER_linux-libc-headers ?= "linux-qoriq-sdk-headers"

KERNEL_IMAGETYPE ?= "uImage"
# disable the images below for now
#		ext2.bz2 ext2.lzma \
#		ext2.gz.u-boot ext2.bz2.u-boot ext2.lzma.u-boot"

MACHINEOVERRIDES .= ":e500mc:fslmachine"
