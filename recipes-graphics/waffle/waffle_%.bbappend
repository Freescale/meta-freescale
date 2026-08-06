FILESEXTRAPATHS:prepend:imxgpu := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu = " \
    file://0001-meson-Add-missing-wayland-dependency-on-EGL.patch \
    file://0002-meson-Separate-surfaceless-option-from-x11.patch \
"

# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target -- measured on qemuarm64 with bitbake -e,
# meta-freescale in and out of BBLAYERS.

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_IMXGPU_X11 = ""
PACKAGECONFIG_IMXGPU_X11:imxgpu3d = "x11-egl glx"
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_IMXGPU_GBM = "gbm"
PACKAGECONFIG_IMXGPU_GBM:mx6-nxp-bsp = ""
PACKAGECONFIG_IMXGPU_GBM:mx7-nxp-bsp = ""
PACKAGECONFIG:imxgpu = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland',                     'wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', '${PACKAGECONFIG_IMXGPU_X11}', \
                                                                                  '', d), d)} \
    ${PACKAGECONFIG_IMXGPU_GBM} \
"
