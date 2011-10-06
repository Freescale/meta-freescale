# Copyright (C) 2011 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "AMD libz160 gpu driver"
LICENSE = "MIT"
SECTION = "libs"
PR = "r1"

#todo: Replace for correct AMD license
LIC_FILES_CHKSUM = "file://usr/include/z160.h;endline=28;md5=65dd44cd769091092f38e34cd52cc271"

SRC_URI = "http://auslxsc01.mtwk.freescale.net/ppp/${PN}-bin-${PV}.tar.gz"
SRC_URI[md5sum] = "f934ab967285aad10ec4bb0263ad549f"
SRC_URI[sha256sum] = "ead4aab7eecad85a591e72996f785057846a7f9801e86ad6441c47862cceac93"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -m 0755 ${S}/usr/lib/* ${D}${libdir}
    install -m 0644 ${S}/usr/include/* ${D}${includedir}
}

S = "${WORKDIR}/${PN}-bin-${PV}"
FILES_${PN} = "${libdir}/*.so*"
FILES_${PN}-dev = "${includedir}/*.h"
