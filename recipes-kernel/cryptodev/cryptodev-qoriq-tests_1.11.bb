require recipes-kernel/cryptodev/cryptodev-tests_${PV}.bb
require cryptodev-qoriq-${PV}.inc

SRC_URI += " \
file://0001-Add-the-compile-and-install-rules-for-cryptodev-test.patch \
"

COMPATIBLE_MACHINE = "(qoriq)"
