# common providers of QorIQ targets
PREFERRED_PROVIDER_cryptodev-linux = "cryptodev-qoriq-linux"
PREFERRED_PROVIDER_cryptodev-module = "cryptodev-qoriq-module"
PREFERRED_PROVIDER_cryptodev-tests = "cryptodev-qoriq-tests"
PREFERRED_PROVIDER_openssl = "openssl-qoriq"
PREFERRED_PROVIDER_u-boot ?= "u-boot-qoriq"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-qoriq"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq"
PREFERRED_PROVIDER_qemu = "qemu-qoriq"
PREFERRED_VERSION_testfloat = "2a"

IMAGE_CLASSES += "image_types_uboot"

SOC_DEFAULT_IMAGE_FSTYPES = "tar.gz ext2.gz.u-boot ext2.gz"
IMAGE_FSTYPES ?= "${SOC_DEFAULT_IMAGE_FSTYPES}"

MACHINE_FEATURES ?= "pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS += "udev-extraconf udev-rules-qoriq kernel-modules"

EXTRA_IMAGEDEPENDS += "u-boot cst-native"

MACHINEOVERRIDES =. "qoriq:"

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""
MACHINE_SOCARCH_SUFFIX_qoriq = "-qoriq"

MACHINE_ARCH_FILTER = "virtual/kernel"
MACHINE_SOCARCH_FILTER_append_qoriq = " cryptodev-qoriq-linux cryptodev-qoriq-module cryptodev-qoriq-test openssl-qoriq"

INHERIT += "fsl-dynamic-packagearch"
