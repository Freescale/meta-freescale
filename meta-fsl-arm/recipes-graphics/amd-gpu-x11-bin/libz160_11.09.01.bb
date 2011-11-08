# Copyright (C) 2011 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "AMD libz160 gpu driver"
LICENSE = "MIT"
SECTION = "libs"
PR = "r0"

#todo: Replace for correct AMD license
LIC_FILES_CHKSUM = "file://usr/include/z160.h;endline=28;md5=65dd44cd769091092f38e34cd52cc271"

SRC_URI = "file://${PN}-bin-${PV}.tar.gz"
SRC_URI[md5sum] = "49b6d51e2ea6651107b08f43715c8c2e"
SRC_URI[sha256sum] = "43b1bebb2656d0c868c10f66ddc064c6324b74694daedfb3f542f93f438232c5"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -m 0755 ${S}/usr/lib/* ${D}${libdir}
    install -m 0644 ${S}/usr/include/* ${D}${includedir}
}

S = "${WORKDIR}/${PN}-bin-${PV}"
FILES_${PN} = "${libdir}/*.so*"
FILES_${PN}-dev = "${includedir}/*.h"
