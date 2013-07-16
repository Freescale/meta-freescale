require conf/machine/include/tune-ppce500v2.inc

MACHINE_FEATURES = "kernel26 pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS = "u-boot udev-extraconf"

# FIXME
# Workaround for ICE of gcc-4.8.x when passing -mfloat-gprs=double
# Bug: http://gcc.gnu.org/bugzilla/show_bug.cgi?id=57717#c1
# Replace double-float with single-float, revert the commit when above bug is fixed
TUNE_CCARGS := "${@d.getVar('TUNE_CCARGS', True).replace('-mfloat-gprs=double', '-mfloat-gprs=single')}"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq-sdk"
PREFERRED_VERSION_virtual/kernel ?= "3.0.34"
PREFERRED_PROVIDER_linux-libc-headers ?= "linux-qoriq-sdk-headers"
PREFERRED_VERSION_qemu = "1.4+fsl"

KERNEL_IMAGETYPE ?= "uImage"
# disable the images below for now
#		ext2.bz2 ext2.lzma \
#		ext2.gz.u-boot ext2.bz2.u-boot ext2.lzma.u-boot"

MACHINEOVERRIDES .= ":e500v2:fslmachine"
