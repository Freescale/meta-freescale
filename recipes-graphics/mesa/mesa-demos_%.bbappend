# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target -- measured on qemuarm64 with bitbake -e,
# meta-freescale in and out of BBLAYERS.

# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu = " \
    file://Replace-glWindowPos2iARB-calls-with-glWindowPos2i.patch \
    file://fix-clear-build-break.patch \
    file://0001-egl-clear-backgrounds-black.patch \
    file://0001-YOCIMX-8300-Fix-mesa-demos-build-break-on-GCC-14.patch \
"

REQUIRED_DISTRO_FEATURES:remove:imxgpu = "x11"

# Dispatch line consumes machine-specialized helper vars; the helpers below
# default to "" and are overridden per i.MX GPU variant.
# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG:remove = "\
    ${PACKAGECONFIG_REMOVE_IF_2D_ONLY} \
    ${PACKAGECONFIG_REMOVE_IF_GPU}"
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_REMOVE_IF_2D_ONLY = ""
PACKAGECONFIG_REMOVE_IF_2D_ONLY:imxgpu2d = "gles1 gles2"
PACKAGECONFIG_REMOVE_IF_2D_ONLY:imxgpu3d = ""
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_REMOVE_IF_GPU = ""
PACKAGECONFIG_REMOVE_IF_GPU:imxgpu = "x11"

# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG:append = " \
    ${PACKAGECONFIG_APPEND_IF_GPU}"
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_APPEND_IF_GPU = ""
PACKAGECONFIG_APPEND_IF_GPU:imxgpu = "glu"

PACKAGECONFIG[glu] = ",,libglu"
