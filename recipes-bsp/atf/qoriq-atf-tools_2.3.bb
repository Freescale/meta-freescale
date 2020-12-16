require qoriq-atf-${PV}.inc

SUMMARY = "Tools for ARM Trusted Firmware, e.g. FIP image creation tool"

DEPENDS += "openssl"

EXTRA_OEMAKE = "fiptool V=1 HOSTCC='${CC} ${CPPFLAGS} ${CFLAGS} ${LDFLAGS}'"

do_install () {
    install -m 0755 -d ${D}/${bindir}
    install -m 0755 ${S}/tools/fiptool/fiptool ${D}/${bindir}/
}

BBCLASSEXTEND = "native"
