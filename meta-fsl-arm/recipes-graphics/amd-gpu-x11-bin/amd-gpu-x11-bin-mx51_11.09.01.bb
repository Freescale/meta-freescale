# Copyright (C) 2011 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for x11 on mx51"
LICENSE = "MIT"
SECTION = "libs"
PR = "r4"

PROVIDES = "virtual/libgl"
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
    cp -L ${S}/usr/lib/*.so ${D}${libdir}
    cp -axr ${S}/usr/include/* ${D}${includedir}

    find ${D}${bindir} -type f -exec chmod 755 {} \;
    find ${D}${libdir} -type f -exec chmod 755 {} \;
    find ${D}${includedir} -type f -exec chmod 660 {} \;
}

INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dev = "ldflags"

# Todo: find out what libres.a and libcsi.a are for
# package include/KHR header
PACKAGES = "libegl libegl-dev libgl libgles libgles-dev libopenvg \
            libopenvg-dev lib2dz160 libd2z430"
FILES_libegl = "${libdir}/libEGL.*"
FILES_libegl-dev = "${includedir}/EGL"
FILES_libgl = "${libdir}/libgsl-fsl.*"
FILES_libgles = "${libdir}/libGLES*"
FILES_libgles-dev = "${includedir}/GLES ${includedir}/GLES2"
FILES_libopenvg = "${libdir}/libOpenVG.*"
FILES_libopenvg-dev = "${includedir}/VG"
FILES_lib2dz160 = "${libdir}/lib2dz160.*"
FILES_lib2dz340 = "${libdir}/lib2dz340.*"
