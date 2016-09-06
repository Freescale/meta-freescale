# Copyright (C) 2013 Eric Bénard - Eukréa Electromatique
# Copyright (C) 2016 O.S. Systems Software LTDA.

HAS_X11 = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 1, 0, d)}"

IMXGPU_imxgpu3d = "3d"
IMXGPU_imxgpu2d = "2d"

PACKAGECONFIG_GL_imxpxp   = "gles2"
PACKAGECONFIG_GL_imxgpu3d = "gles2"
PACKAGECONFIG_GL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' gl', '', d)}"

QT_CONFIG_FLAGS_APPEND = ""
QT_CONFIG_FLAGS_APPEND_imxpxp = "${@base_contains('DISTRO_FEATURES', 'x11', ' -no-eglfs', ' -eglfs', d)}"
QT_CONFIG_FLAGS_APPEND_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' -no-eglfs', ' -no-opengl -linuxfb -no-eglfs', d)}"
QT_CONFIG_FLAGS_APPEND_imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' -no-eglfs', ' -eglfs', d)}"
QT_CONFIG_FLAGS_append = " ${QT_CONFIG_FLAGS_APPEND}"

do_configure_prepend_mx6() {
    # adapt qmake.conf to our needs
    sed -i 's!load(qt_config)!!' ${S}/mkspecs/linux-oe-g++/qmake.conf
    if test ${HAS_X11} -eq 0; then
        if [ "${IMXGPU}" = "3d"; then
    
            cat >> ${S}/mkspecs/linux-oe-g++/qmake.conf <<EOF
IMX6_CFLAGS             = -DLINUX=1 -DEGL_API_FB=1
EGLFS_DEVICE_INTEGRATION = eglfs_viv
EOF
        else
            cat >> ${S}/mkspecs/linux-oe-g++/qmake.conf <<EOF
IMX6_CFLAGS             = -DLINUX=1
EOF
        fi
    else
        cat >> ${S}/mkspecs/linux-oe-g++/qmake.conf <<EOF
IMX6_CFLAGS             = -DLINUX=1 -DEGL_API_FB=1
EOF
    fi
    cat >> ${S}/mkspecs/linux-oe-g++/qmake.conf <<EOF
QMAKE_LIBS_EGL         += -lEGL
QMAKE_LIBS_OPENGL_ES2  += -lGLESv2 -lEGL -lGAL
QMAKE_LIBS_OPENVG      += -lOpenVG -lEGL -lGAL
QMAKE_CFLAGS_RELEASE   += \$\$IMX6_CFLAGS
QMAKE_CXXFLAGS_RELEASE += \$\$IMX6_CFLAGS
QMAKE_CFLAGS_DEBUG   += \$\$IMX6_CFLAGS
QMAKE_CXXFLAGS_DEBUG += \$\$IMX6_CFLAGS

load(qt_config)

EOF
}
