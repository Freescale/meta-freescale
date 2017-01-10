# Copyright (C) 2013 Eric Bénard - Eukréa Electromatique
# Copyright (C) 2016 O.S. Systems Software LTDA.
# Copyright (C) 2016 Freescale Semiconductor

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu2d = "file://0014-Add-IMX-GPU-support.patch"
SRC_URI_append_imxgpu3d = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', 'file://0015-Add-eglfs-to-IMX-GPU.patch', d)} \
"

PACKAGECONFIG_GL_imxpxp   = "gles2"
PACKAGECONFIG_GL_imxgpu3d = "gles2"
PACKAGECONFIG_GL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' gl', '', d)}"

QT_CONFIG_FLAGS_APPEND = ""
QT_CONFIG_FLAGS_APPEND_imxpxp = "${@base_contains('DISTRO_FEATURES', 'x11', ' -no-eglfs', ' -eglfs', d)}"
QT_CONFIG_FLAGS_APPEND_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' -no-eglfs', ' -no-opengl -linuxfb -no-eglfs', d)}"
QT_CONFIG_FLAGS_APPEND_imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' -no-eglfs', ' -eglfs', d)}"
QT_CONFIG_FLAGS_append = " ${QT_CONFIG_FLAGS_APPEND}"
