require cryptodev-qoriq_${PV}.inc

SUMMARY = "A /dev/crypto device driver kernel module"

PROVIDES = "cryptodev-module"

inherit module qoriq_build_64bit_kernel

# Header file provided by a separate package
DEPENDS += "cryptodev-linux"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" DESTDIR="${D}"'

SRC_URI_append = " \
file://0001-Disable-installing-header-file-provided-by-another-p.patch \
"

RCONFLICTS_${PN} = "ocf-linux"
RREPLACES_${PN} = "ocf-linux"

COMPATIBLE_MACHINE = "(qoriq)"
