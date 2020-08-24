require recipes-kernel/cryptodev/cryptodev-module_${PV}.bb
require cryptodev-qoriq-${PV}.inc

inherit qoriq_build_64bit_kernel

SRC_URI += " \
file://0001-Disable-installing-header-file-provided-by-another-p.patch \
"

COMPATIBLE_MACHINE = "(qoriq)"
