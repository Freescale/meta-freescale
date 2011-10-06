# Copyright (C) 2011 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for x11 on mx51"
LICENSE = "MIT"
SECTION = "libs"
PR = "r1"

#todo: Replace for correct AMD license
LIC_FILES_CHKSUM = "file://usr/include/VG/openvg.h;endline=30;md5=b0109611dd76961057d4c45ae6519802"
DEPENDS = "virtual/libx11 libz160"

SRC_URI = "http://auslxsc01.mtwk.freescale.net/ppp/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "f2b0bc679504dc11d301d725c280e555"
SRC_URI[sha256sum] = "bc3e6aa3e67fbc42c14f832a8ba677b7531e01dba26dff96190d71c109f1e34a"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${bindir}
    install -d ${D}${includedir}/EGL
    install -d ${D}${includedir}/GLES
    install -d ${D}${includedir}/GLES2
    install -d ${D}${includedir}/KHR
    install -d ${D}${includedir}/VG
    install -m 0644 ${S}/usr/include/EGL/* ${D}${includedir}/EGL
    install -m 0644 ${S}/usr/include/GLES/* ${D}${includedir}/GLES
    install -m 0644 ${S}/usr/include/GLES2/* ${D}${includedir}/GLES2
    install -m 0644 ${S}/usr/include/KHR/* ${D}${includedir}/KHR
    install -m 0644 ${S}/usr/include/VG/* ${D}${includedir}/VG
    install -m 0755 ${S}/usr/lib/* ${D}${libdir}
    install -m 0755 ${S}/usr/bin/* ${D}${bindir}
}

FILES_${PN} = "${bindir}/* ${libdir}/*.so*"
FILES_${PN}-dev = "\
    ${includedir}/EGL/*.h \
    ${includedir}/GLES/*.h \
    ${includedir}/GLES2/*.h \
    ${includedir}/KHR/*.h \
    ${includedir}/VG/*.h"
FILES_${PN}-staticdev = "${libdir}/*.a"
