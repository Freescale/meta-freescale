require cryptodev-qoriq_${PV}.inc

SUMMARY = "A test suite for /dev/crypto device driver"
DEPENDS = "openssl"

PROVIDES = "cryptodev-tests"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'

do_compile() {
	oe_runmake testprogs
}

do_install() {
	oe_runmake install_tests
}

FILES_${PN}-dbg += "${bindir}/tests_cryptodev/.debug"
FILES_${PN} = "${bindir}/tests_cryptodev/*"

COMPATIBLE_MACHINE = "(qoriq-arm|qoriq-ppc)"
