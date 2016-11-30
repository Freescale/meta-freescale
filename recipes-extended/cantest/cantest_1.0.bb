DESCRIPTION = "cantest"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d85064f0fa931974668d281ab83cc97e"

SRC_URI = "git://git.freescale.com/ppc/sdk/cantest.git;branch=sdk-v2.0.x"
SRCREV = "0ad5fa86b1007aefef60a10ccb3a946497477995"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${bindir}
    install -m 755 cantest ${D}${bindir}
}

COMPATIBLE_MACHINE = "(e500v2)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

