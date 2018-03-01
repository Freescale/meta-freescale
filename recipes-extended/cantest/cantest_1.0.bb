DESCRIPTION = "cantest"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d85064f0fa931974668d281ab83cc97e"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/cantest;nobranch=1"
SRCREV = "9a9735862dce192de782550d0ab1a50f163b537c"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'

do_install() {
    install -d ${D}${bindir}
    install -m 755 cantest ${D}${bindir}
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

