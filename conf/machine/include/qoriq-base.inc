# common providers of QorIQ targets
PREFERRED_PROVIDER_cryptodev-linux = "cryptodev-qoriq-linux"
PREFERRED_PROVIDER_cryptodev-module = "cryptodev-qoriq-module"
PREFERRED_PROVIDER_cryptodev-tests = "cryptodev-qoriq-tests"
PREFERRED_PROVIDER_u-boot ?= "u-boot-qoriq"
PREFERRED_PROVIDER_virtual/bootloader ?= "${PREFERRED_PROVIDER_u-boot}"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq"
PREFERRED_VERSION_testfloat = "2a"
PREFERRED_PROVIDER_ptpd = "ptpd-qoriq"

SOC_DEFAULT_IMAGE_FSTYPES = "tar.gz ext2.gz.u-boot ext2.gz"
IMAGE_FSTYPES ?= "${SOC_DEFAULT_IMAGE_FSTYPES}"

MACHINE_FEATURES ?= "pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS += "udev-extraconf udev-rules-qoriq kernel-modules"

EXTRA_IMAGEDEPENDS += "u-boot cst-native"

MACHINEOVERRIDES =. "qoriq:"

# Machines or distros can define which BSP it should use by default. We are
# intending to default for nxp BSP by default and specific machines or
# DISTROs might change it if need.
#
# Two values are considered valid: mainline, nxp
QORIQ_DEFAULT_BSP ?= "nxp"

MACHINEOVERRIDES =. "use-${QORIQ_DEFAULT_BSP}-bsp:"

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""
MACHINE_SOCARCH_SUFFIX_qoriq = "-qoriq"

MACHINE_ARCH_FILTER = "virtual/kernel"
MACHINE_SOCARCH_FILTER_append_qoriq = " cryptodev-qoriq-linux cryptodev-qoriq-module cryptodev-qoriq-test openssl-qoriq"

INHERIT += "fsl-dynamic-packagearch"
