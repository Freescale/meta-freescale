# Copyright 2018 (C) O.S. Systems Software LTDA.
SUMMARY = "Samples for OpenGL ES"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=aeb969185a143c3c25130bc2c3ef9a50"
DEPENDS = "zlib libpng procps"

SRC_URI = "git://github.com/nxp-imx/apitrace-imx.git;protocol=https;branch=imx_10.0 \
           file://0001-dlsym-workaround-glibc-2.34-build-failure.patch \
"
SRCREV = "522cb2981289b7ba20d6dd4b4bf75097e079815b"

S = "${WORKDIR}/git"

inherit cmake pkgconfig perlnative python3native

PACKAGECONFIG_BACKEND:mx6-nxp-bsp = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'waffle', \
       bb.utils.contains('DISTRO_FEATURES',     'x11',    'x11', \
                                                             '', d), d)} \
"
PACKAGECONFIG_BACKEND:mx7-nxp-bsp  = "${PACKAGECONFIG_BACKEND:mx6-nxp-bsp}"
PACKAGECONFIG_BACKEND:mx8-nxp-bsp  = "waffle"
PACKAGECONFIG_BACKEND:mx95-nxp-bsp = "waffle"

PACKAGECONFIG_GPU2D              = ""
PACKAGECONFIG_GPU2D:imxgpu2d     = "vivante"
PACKAGECONFIG_GPU2D:mx95-nxp-bsp = ""

PACKAGECONFIG ??= " \
    egl \
    ${PACKAGECONFIG_BACKEND} \
    ${PACKAGECONFIG_GPU2D} \
"

PACKAGECONFIG[egl] = "-DENABLE_EGL=ON,-DENABLE_EGL=OFF,virtual/egl"
PACKAGECONFIG[gui] = "-DENABLE_GUI=ON,-DENABLE_GUI=OFF"
PACKAGECONFIG[multiarch] = "-DENABLE_MULTIARCH=ON,-DENABLE_MULTIARCH=OFF"
PACKAGECONFIG[waffle] = "-DENABLE_WAFFLE=ON,-DENABLE_WAFFLE=OFF,waffle"
PACKAGECONFIG[x11] = "-DENABLE_X11=ON,-DENABLE_X11=OFF"
PACKAGECONFIG[vivante] = "-DENABLE_VIVANTE=ON,-DENABLE_VIVANTE=OFF,virtual/libg2d"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/apitrace/scripts/* \
    ${libdir}/apitrace/wrappers/* \
"
EXTRA_OECMAKE += "\
    -DENABLE_GUI=OFF \
    -DENABLE_STATIC_LIBGCC=OFF \
    -DENABLE_STATIC_LIBSTDCXX=OFF \
    -DPython3_ROOT_DIR=/usr/bin/python3-native \
"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imxgpu)"
SECURITY_CFLAGS:toolchain-clang = ""

