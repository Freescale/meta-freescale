# Provides the QorIQ common settings
require conf/machine/include/soc-family.inc

# common providers of QorIQ targets
PREFERRED_PROVIDER_cryptodev-linux = "cryptodev-qoriq-linux"
PREFERRED_PROVIDER_cryptodev-module = "cryptodev-qoriq-module"
PREFERRED_PROVIDER_cryptodev-tests = "cryptodev-qoriq-tests"
PREFERRED_PROVIDER_openssl = "openssl-qoriq"
PREFERRED_PROVIDER_u-boot ?= "u-boot-qoriq"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-qoriq"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq"
PREFERRED_PROVIDER_qemu = "qemu-qoriq"

IMAGE_CLASSES += "image_types_uboot"

MACHINE_FEATURES ?= "pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS += "udev-extraconf kernel-modules"

EXTRA_IMAGEDEPENDS += "u-boot cst-native"

MACHINEOVERRIDES =. "qoriq:"
