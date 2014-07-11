TARGET_FPU = "hard"

require conf/machine/include/tune-ppce6500.inc

MACHINE_FEATURES = "keyboard pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS = "u-boot udev-extraconf kernel-modules"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq-sdk"
PREFERRED_VERSION_qemu = "1.7+fsl"
PREFERRED_VERSION_openssl = "1.0.1g"

KERNEL_IMAGETYPE ?= "uImage"
# disable the images below for now
#		ext2.bz2 ext2.lzma \
#		ext2.gz.u-boot ext2.bz2.u-boot ext2.lzma.u-boot"

MACHINEOVERRIDES .= ":e6500:fslmachine"
