SUMMARY = "Tools for ARM Trusted Firmware, e.g. FIP image creation tool"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://license.rst;md5=e927e02bca647e14efd87e9e914b2443"

SRC_URI = "git://github.com/nxp-qoriq/atf;protocol=https;nobranch=1"
SRCREV = "7d748e6f0ec652ba7c43733dc67a3d0b0217390a"

S = "${WORKDIR}/git"

DEPENDS += "openssl"

EXTRA_OEMAKE = "fiptool V=1 HOSTCC='${CC} ${CPPFLAGS} ${CFLAGS} ${LDFLAGS}'"

do_install () {
    install -m 0755 -d ${D}/${bindir}
    install -m 0755 ${S}/tools/fiptool/fiptool ${D}/${bindir}/
}

BBCLASSEXTEND = "native"
