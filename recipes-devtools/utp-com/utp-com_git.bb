DESCRIPTION = "Tool used to send commands to hardware via NXP's UTP protocol"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8264535c0c4e9c6c335635c4026a8022"

DEPENDS = "sg3-utils"

SRCREV = "dee512ced1e9367d223d22f10797fbf9aeacfab6"
SRC_URI = " \
    git://github.com/Freescale/utp_com;protocol=https;branch=master \
"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"

do_compile () {
	oe_runmake
}

do_install () {
    install -d -m 0755 ${D}${bindir}
    install -m 0755 ${S}/utp_com ${D}${bindir}/utp_com
}

BBCLASSEXTEND = "native nativesdk"
