SUMMARY = "Tools for ARM Trusted Firmware, e.g. FIP image creation tool"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://license.rst;md5=e927e02bca647e14efd87e9e914b2443"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/atf;nobranch=1"
SRCREV = "17f94e4315e81e3d1b22d863d9614d724e8273dc"

S = "${WORKDIR}/git"

DEPENDS += "openssl"

EXTRA_OEMAKE = "fiptool V=1 HOSTCC='${CC} ${CPPFLAGS} ${CFLAGS} ${LDFLAGS}'"

do_install () {
    install -m 0755 -d ${D}/${bindir}
    install -m 0755 ${S}/tools/fiptool/fiptool ${D}/${bindir}/
}

BBCLASSEXTEND = "native"
