# Copyright (C) 2013 Eric Bénard - Eukréa Electromatique

HAS_X11 = "${@base_contains('DISTRO_FEATURES', 'x11', 1, 0, d)}"

GL_DEPENDS_mx6 = "${@base_contains('DISTRO_FEATURES', 'x11', '', 'virtual/libgles2 virtual/egl', d)}"
QT_GLFLAGS_mx6 = "${@base_contains('DISTRO_FEATURES', 'x11', '', '-opengl es2 -eglfs', d)}"
QT_EXAMPLES_mx6 = "-make examples"
QT_DEMOS_mx6 = "-make demos"

GL_DEPENDS_mx5 = "${@base_contains('DISTRO_FEATURES', 'x11', '', 'virtual/libgles2 virtual/egl', d)}"
QT_GLFLAGS_mx5 = "${@base_contains('DISTRO_FEATURES', 'x11', '', '-opengl es2 -eglfs', d)}"
QT_EXAMPLES_mx5 = "-make examples"
QT_DEMOS_mx5 = "-make demos"

TSLIB_DEPENDS_mx6 = "tslib"
QT_TSLIB_mx6 = "-tslib"

TSLIB_DEPENDS_mx5 = "tslib"
QT_TSLIB_mx5 = "-tslib"

PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
PACKAGE_ARCH_mx5 = "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend_mx5 := "${THISDIR}/${PN}:"
SRC_URI_append_mx5 += " \
    file://qeglfshooks_imx5.cpp \
    "

do_configure_prepend_mx6() {
    if test ${HAS_X11} -eq 0; then
# adapt qmake.conf to our needs
sed -i 's!load(qt_config)!!' ${S}/mkspecs/linux-oe-g++/qmake.conf
cat >> ${S}/mkspecs/linux-oe-g++/qmake.conf <<EOF
EGLFS_PLATFORM_HOOKS_SOURCES = \$\$PWD/qeglfshooks_imx6.cpp
IMX6_CFLAGS             = -DLINUX=1 -DEGL_API_FB=1
QMAKE_LIBS_EGL         += -lEGL
QMAKE_LIBS_OPENGL_ES2  += -lGLESv2 -lEGL -lGAL
QMAKE_LIBS_OPENVG      += -lOpenVG -lEGL -lGAL
QMAKE_CFLAGS_RELEASE   += \$\$IMX6_CFLAGS
QMAKE_CXXFLAGS_RELEASE += \$\$IMX6_CFLAGS
QMAKE_CFLAGS_DEBUG   += \$\$IMX6_CFLAGS
QMAKE_CXXFLAGS_DEBUG += \$\$IMX6_CFLAGS
QMAKE_CFLAGS_EGL += \$\$IMX6_CFLAGS
load(qt_config)

EOF

# copy the hook in the mkspecs directory OE is using
cp ${S}/mkspecs/devices/linux-imx6-g++/qeglfshooks_imx6.cpp ${S}/mkspecs/linux-oe-g++/
    fi
}

do_configure_prepend_mx5() {
    if test ${HAS_X11} -eq 0; then
# adapt qmake.conf to our needs
sed -i 's!load(qt_config)!!' ${S}/mkspecs/linux-oe-g++/qmake.conf
cat >> ${S}/mkspecs/linux-oe-g++/qmake.conf <<EOF
EGLFS_PLATFORM_HOOKS_SOURCES = \$\$PWD/qeglfshooks_imx5.cpp
IMX5_CFLAGS             = -D_LINUX
QMAKE_LIBS_EGL         += -lEGL
QMAKE_LIBS_OPENGL_ES2  += -lGLESv2 -lEGL
QMAKE_LIBS_OPENVG      += -lOpenVG -lEGL
QMAKE_CFLAGS_RELEASE   += \$\$IMX5_CFLAGS
QMAKE_CXXFLAGS_RELEASE += \$\$IMX5_CFLAGS
QMAKE_CFLAGS_DEBUG   += \$\$IMX5_CFLAGS
QMAKE_CXXFLAGS_DEBUG += \$\$IMX5_CFLAGS
QMAKE_CFLAGS_EGL += \$\$IMX5_CFLAGS
load(qt_config)

EOF

cp ${WORKDIR}/qeglfshooks_imx5.cpp ${S}/mkspecs/linux-oe-g++/
    fi
}
