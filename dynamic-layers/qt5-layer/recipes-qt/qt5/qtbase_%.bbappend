# Copyright (C) 2013 Eric Bénard - Eukréa Electromatique
# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2016, 2017 O.S. Systems Software LTDA.
# Copyright (C) 2017-2018 NXP

# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target. Not parsed in this tree (the trigger
# layer for this dynamic-layers directory is not checked out), so argued from
# the metadata rather than measured.

# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu = " \
    file://0014-Add-IMX-GPU-support.patch \
    file://0001-egl.prf-Fix-build-error-when-egl-headers-need-platfo.patch \
"

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_GL_IMX_GPU = ""
PACKAGECONFIG_GL_IMX_GPU:mx8-nxp-bsp = "gbm kms"
PACKAGECONFIG_GL_IMX_GPU:mx95-nxp-bsp = "gbm kms"

PACKAGECONFIG_GL:imxpxp = "gles2"
PACKAGECONFIG_GL:imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' gl', '', d)} \
                             ${PACKAGECONFIG_GL_IMX_GPU}"
PACKAGECONFIG_GL:imxgpu3d = "gles2 \
                             ${PACKAGECONFIG_GL_IMX_GPU}"
PACKAGECONFIG_GL:use-mainline-bsp ?= "gles2 gbm kms"

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_PLATFORM = ""
PACKAGECONFIG_PLATFORM:imxgpu2d = "no-opengl linuxfb"
PACKAGECONFIG_PLATFORM:imxgpu3d = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11',     '', \
       bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
                                                       'eglfs', d), d)}"
PACKAGECONFIG_PLATFORM:use-mainline-bsp = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', 'eglfs', d)}"
# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG += "${PACKAGECONFIG_PLATFORM}"

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_VULKAN_IMX_GPU = ""
PACKAGECONFIG_VULKAN_IMX_GPU:mx8-nxp-bsp = "vulkan"
PACKAGECONFIG_VULKAN_IMX_GPU:mx9-nxp-bsp = "vulkan"
PACKAGECONFIG_VULKAN_IMX_GPU:mx8mm-nxp-bsp = ""
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_VULKAN = ""
PACKAGECONFIG_VULKAN:imxgpu = "${PACKAGECONFIG_VULKAN_IMX_GPU}"
# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG += "${@bb.utils.contains('DISTRO_FEATURES', 'vulkan', '${PACKAGECONFIG_VULKAN}', '', d)}"
