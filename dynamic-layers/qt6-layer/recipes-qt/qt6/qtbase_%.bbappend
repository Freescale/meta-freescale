# Copyright (C) 2013 Eric Bénard - Eukréa Electromatique
# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2016, 2017 O.S. Systems Software LTDA.
# Copyright (C) 2017-2018 NXP

PACKAGECONFIG_GRAPHICS:imxpxp = "\
    gles2"
PACKAGECONFIG_GRAPHICS:imxgpu2d = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' gl', '', d)} \
    ${PACKAGECONFIG_GRAPHICS_IMX_GPU}"
PACKAGECONFIG_GRAPHICS:imxgpu3d = "\
    gles2 \
    ${PACKAGECONFIG_GRAPHICS_IMX_GPU}"
# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target. Not parsed in this tree (the trigger
# layer for this dynamic-layers directory is not checked out), so argued from
# the metadata rather than measured.

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_GRAPHICS_IMX_GPU = ""
PACKAGECONFIG_GRAPHICS_IMX_GPU:mx8-nxp-bsp = "\
    gbm kms"
PACKAGECONFIG_GRAPHICS_IMX_GPU:mx95-nxp-bsp = "\
    gbm kms"

PACKAGECONFIG_GRAPHICS:use-mainline-bsp ?= "\
    gles2 gbm kms"

# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG += "\
    ${PACKAGECONFIG_PLATFORM}"

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_PLATFORM = ""
PACKAGECONFIG_PLATFORM:imxgpu2d = "\
    no-opengl \
    linuxfb \
    ${PACKAGECONFIG_PLATFORM_EGLFS}"
PACKAGECONFIG_PLATFORM:imxgpu3d = "\
    ${PACKAGECONFIG_PLATFORM_EGLFS}"

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_PLATFORM_EGLFS = ""
PACKAGECONFIG_PLATFORM_EGLFS:imxgpu3d = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11',     '', \
       bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
                                                       'eglfs', d), d)}"
PACKAGECONFIG_PLATFORM_EGLFS:mx8-nxp-bsp = "\
    eglfs"

PACKAGECONFIG_PLATFORM:use-mainline-bsp = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', 'eglfs', d)}"

PACKAGECONFIG += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${PACKAGECONFIG_WAYLAND}', '', d)}"
PACKAGECONFIG_WAYLAND = "wayland"

# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'vulkan', '${PACKAGECONFIG_VULKAN}', '', d)}"
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_VULKAN = ""
PACKAGECONFIG_VULKAN:imxgpu = "\
    ${PACKAGECONFIG_VULKAN_IMX_GPU}"
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_VULKAN_IMX_GPU = ""
PACKAGECONFIG_VULKAN_IMX_GPU:mx8-nxp-bsp = "vulkan"
PACKAGECONFIG_VULKAN_IMX_GPU:mx95-nxp-bsp = "vulkan"
PACKAGECONFIG_VULKAN_IMX_GPU:mx8mm-nxp-bsp = ""
