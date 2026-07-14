SUMMARY = "NXP UTP protocol command-line tool"
DESCRIPTION = "Tool used to send commands to hardware via NXP's UTP protocol"
HOMEPAGE = "https://github.com/Freescale/utp_com"
SECTION = "devel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8264535c0c4e9c6c335635c4026a8022"

DEPENDS = "sg3-utils"

PV = "1.0+git${SRCPV}"
SRC_URI = "\
    git://github.com/Freescale/utp_com;protocol=https;branch=master \
"
SRCREV = "dee512ced1e9367d223d22f10797fbf9aeacfab6"

do_configure[noexec] = "1"

do_compile () {
    oe_runmake
}

do_install () {
    install -d -m 0755 ${D}${bindir}
    install -m 0755 ${S}/utp_com ${D}${bindir}/utp_com
}

BBCLASSEXTEND = "native nativesdk"
