# Copyright (C) 2013 Eric Bénard - Eukréa Electromatique
# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2016, 2017 O.S. Systems Software LTDA.
# Copyright (C) 2017 NXP

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_use-mainline-bsp = " file://Environment-variable-enabling-the-workaround-FBO-rea.patch"
SRC_URI_append_imxgpu2d = "file://0014-Add-IMX-GPU-support.patch"
SRC_URI_append_imxgpu3d = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', \
                                                   'file://0015-Add-eglfs-to-IMX-GPU.patch \
                                                    file://0016-Configure-eglfs-with-egl-pkg-config.patch', d)} \
"

PACKAGECONFIG_GL_imxpxp   = "gles2"
PACKAGECONFIG_GL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' gl', '', d)}"
PACKAGECONFIG_GL_imxgpu3d = "gles2"
PACKAGECONFIG_GL_use-mainline-bsp  = "gles2 kms"

QT_CONFIG_FLAGS_APPEND = ""
QT_CONFIG_FLAGS_APPEND_imxpxp = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '-no-eglfs', '-eglfs', d)}"
QT_CONFIG_FLAGS_APPEND_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '-no-eglfs', '-no-opengl -linuxfb -no-eglfs', d)}"
QT_CONFIG_FLAGS_APPEND_imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '-no-eglfs', '-eglfs', d)}"
QT_CONFIG_FLAGS_APPEND_use-mainline-bsp =  "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '-no-eglfs', '-eglfs', d)}"
QT_CONFIG_FLAGS_append = " ${QT_CONFIG_FLAGS_APPEND}"
