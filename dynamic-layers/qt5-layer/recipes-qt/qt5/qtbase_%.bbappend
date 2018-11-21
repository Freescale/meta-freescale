# Copyright (C) 2013 Eric Bénard - Eukréa Electromatique
# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2016, 2017 O.S. Systems Software LTDA.
# Copyright (C) 2017-2018 NXP

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu2d = " \
    file://0014-Add-IMX-GPU-support.patch \
    file://0001-egl.prf-Fix-build-error-when-egl-headers-need-platfo.patch \
"
SRC_URI_APPEND_3D_NOT_X11 = " \
    file://0015-Add-eglfs-to-IMX-GPU.patch \
    file://0016-Configure-eglfs-with-egl-pkg-config.patch \
"
SRC_URI_append_imxgpu3d = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', '${SRC_URI_APPEND_3D_NOT_X11}', d)} \
"

PACKAGECONFIG_GL_imxpxp   = "gles2"
PACKAGECONFIG_GL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' gl', '', d)}"
PACKAGECONFIG_GL_imxgpu3d = "gles2"
PACKAGECONFIG_GL_append_use-mainline-bsp = " gbm kms"

PACKAGECONFIG_PLATFORM          = ""
PACKAGECONFIG_PLATFORM_imxgpu2d = "no-opengl linuxfb"
PACKAGECONFIG_PLATFORM_imxgpu3d = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11',     '', \
       bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
                                                       'eglfs', d), d)}"
PACKAGECONFIG_PLATFORM_use-mainline-bsp = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', 'eglfs', d)}"
PACKAGECONFIG += "${PACKAGECONFIG_PLATFORM}"
