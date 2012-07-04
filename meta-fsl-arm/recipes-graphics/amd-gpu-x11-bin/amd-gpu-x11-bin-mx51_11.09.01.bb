# Copyright (C) 2011 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for x11 on mx51"
LICENSE = "MIT"
SECTION = "libs"
PR = "r6"

#todo: Replace for correct AMD license
LIC_FILES_CHKSUM = "file://usr/include/VG/openvg.h;endline=30;md5=b0109611dd76961057d4c45ae6519802"
DEPENDS = "virtual/libx11 libz160"
RDEPENDS = "libz160"

SRC_URI = "file://${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "499a2cc08dd48a6c5f7db8d231428548"
SRC_URI[sha256sum] = "6150d3d72a3f8abb26df1e31cee0f07f53c106c8d5de014c1175c8cb721fac29"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${bindir}
    install -d ${D}${includedir}

    cp -axr ${S}/usr/bin/* ${D}${bindir}
    cp -L ${S}/usr/lib/*.so* ${D}${libdir}
    cp -axr ${S}/usr/include/* ${D}${includedir}

    find ${D}${bindir} -type f -exec chmod 755 {} \;
    find ${D}${libdir} -type f -exec chmod 755 {} \;
    find ${D}${includedir} -type f -exec chmod 660 {} \;
}

INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dev = "ldflags"
INSANE_SKIP_${PN}-staticdev = "ldflags"
FILES_${PN} = "${bindir}/* ${libdir}/*.so*"
FILES_${PN}-dev = "\
    ${includedir}/EGL/*.h \
    ${includedir}/GLES/*.h \
    ${includedir}/GLES2/*.h \
    ${includedir}/KHR/*.h \
    ${includedir}/VG/*.h"
FILES_${PN}-staticdev = "${libdir}/*.a"
