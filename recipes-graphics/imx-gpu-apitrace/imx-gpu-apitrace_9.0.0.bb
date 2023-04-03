# Copyright 2018 (C) O.S. Systems Software LTDA.
SUMMARY = "Samples for OpenGL ES"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=aeb969185a143c3c25130bc2c3ef9a50"
DEPENDS = "imx-gpu-viv zlib libpng procps"

SRC_URI = "git://github.com/nxp-imx/apitrace-imx.git;protocol=https;branch=imx_9.0"
SRCREV = "fed74f6f84cb4f48ddace4306c9bf90bf7a9a967"

S = "${WORKDIR}/git"

inherit cmake pkgconfig perlnative python3native

PACKAGECONFIG ??= "egl"
PACKAGECONFIG_append = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
        bb.utils.contains('DISTRO_FEATURES',     'x11', ' x11', \
                                                        '', d), d)}"
PACKAGECONFIG_append_mx8 = " waffle"
PACKAGECONFIG_append_imxgpu2d = " vivante"

PACKAGECONFIG[egl] = "-DENABLE_EGL=ON,-DENABLE_EGL=OFF,virtual/egl"
PACKAGECONFIG[gui] = "-DENABLE_GUI=ON,-DENABLE_GUI=OFF"
PACKAGECONFIG[multiarch] = "-DENABLE_MULTIARCH=ON,-DENABLE_MULTIARCH=OFF"
PACKAGECONFIG[waffle] = "-DENABLE_WAFFLE=ON,-DENABLE_WAFFLE=OFF,waffle"
PACKAGECONFIG[x11] = "-DENABLE_X11=ON,-DENABLE_X11=OFF"
PACKAGECONFIG[vivante] = "-DENABLE_VIVANTE=ON,-DENABLE_VIVANTE=OFF,virtual/libg2d"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
FILES_${PN} += " \
    ${libdir}/apitrace/scripts/* \
    ${libdir}/apitrace/wrappers/* \
"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imxgpu)"
