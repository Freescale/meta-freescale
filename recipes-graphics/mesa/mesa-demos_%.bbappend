# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target -- measured on qemuarm64 with bitbake -e,
# meta-freescale in and out of BBLAYERS.

# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# A PACKAGECONFIG[glu] flag would change the recipe's signature on every
# machine (each flag is a dependency of PACKAGECONFIG); it only ever added
# this dependency, so add it directly.
DEPENDS:append:imxgpu = " libglu"

SRC_URI:append:imxgpu = " \
    file://Replace-glWindowPos2iARB-calls-with-glWindowPos2i.patch \
    file://fix-clear-build-break.patch \
"

REQUIRED_DISTRO_FEATURES:remove:imxgpu = "x11"

# Scoped to imxgpu so the recipe is untouched elsewhere: an unscoped
# dispatch left whitespace in PACKAGECONFIG on every machine.
PACKAGECONFIG:remove:imxgpu = "${PACKAGECONFIG_REMOVE_IF_2D_ONLY} x11"
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_REMOVE_IF_2D_ONLY = ""
PACKAGECONFIG_REMOVE_IF_2D_ONLY:imxgpu2d = "gles1 gles2"
PACKAGECONFIG_REMOVE_IF_2D_ONLY:imxgpu3d = ""
