# Copyright (C) 2013 Eric Bénard - Eukréa Electromatique
# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2016, 2017 O.S. Systems Software LTDA.
# Copyright (C) 2017-2018 NXP

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu = " \
    file://0014-Add-IMX-GPU-support.patch \
    file://0001-egl.prf-Fix-build-error-when-egl-headers-need-platfo.patch \
"

PACKAGECONFIG_GL_IMX_GPU     = ""
PACKAGECONFIG_GL_IMX_GPU:mx8-nxp-bsp = "gbm kms"

PACKAGECONFIG_GL:imxpxp   = "gles2"
PACKAGECONFIG_GL:imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' gl', '', d)} \
                             ${PACKAGECONFIG_GL_IMX_GPU}"
PACKAGECONFIG_GL:imxgpu3d = "gles2 \
                             ${PACKAGECONFIG_GL_IMX_GPU}"
PACKAGECONFIG_GL:use-mainline-bsp ?= "gles2 gbm kms"

PACKAGECONFIG_PLATFORM          = ""
PACKAGECONFIG_PLATFORM:imxgpu2d = "no-opengl linuxfb"
PACKAGECONFIG_PLATFORM:imxgpu3d = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11',     '', \
       bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
                                                       'eglfs', d), d)}"
PACKAGECONFIG_PLATFORM:use-mainline-bsp = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', 'eglfs', d)}"
PACKAGECONFIG += "${PACKAGECONFIG_PLATFORM}"

PACKAGECONFIG_VULKAN_IMX_GPU       = ""
PACKAGECONFIG_VULKAN_IMX_GPU:mx8-nxp-bsp   = "vulkan"
PACKAGECONFIG_VULKAN_IMX_GPU:mx8mm-nxp-bsp = ""
PACKAGECONFIG_VULKAN               = ""
PACKAGECONFIG_VULKAN:imxgpu        = "${PACKAGECONFIG_VULKAN_IMX_GPU}"
PACKAGECONFIG += "${@bb.utils.contains('DISTRO_FEATURES', 'vulkan', '${PACKAGECONFIG_VULKAN}', '', d)}"
