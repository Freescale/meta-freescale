require optee-os.nxp.inc

OPTEE_OS_BRANCH = "lf-6.12.49_2.2.0"
SRCREV = "b3883a773a9d15ec6439f9229e48f540c37e0d00"

do_install:append () {
	install -d ${D}${nonarch_base_libdir}/firmware/
	install -m 644 ${B}/core/*.bin ${D}${nonarch_base_libdir}/firmware/
	install -m 644 ${B}/core/tee-raw.bin ${D}${nonarch_base_libdir}/firmware/tee_${MACHINE}.bin

	# Install embedded TAs
	install -d ${D}${base_libdir}/optee_armtz/
	install -m 444 ${B}/ta/*/*.ta ${D}${base_libdir}/optee_armtz/
}

do_deploy:append () {
	install -d ${DEPLOYDIR}/optee
	install -m 644 ${D}${nonarch_base_libdir}/firmware/* ${DEPLOYDIR}/optee/
}

FILES:${PN} = "${nonarch_base_libdir}/optee_armtz/ ${nonarch_base_libdir}/firmware/"
