FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-meson-Add-missing-wayland-dependency-on-EGL.patch \
    file://0002-meson-Separate-surfaceless-option-from-x11.patch \
"

PACKAGECONFIG_IMXGPU_X11          = ""
PACKAGECONFIG_IMXGPU_X11:imxgpu3d = "x11-egl glx"
PACKAGECONFIG_IMXGPU_GBM          = "gbm"
PACKAGECONFIG_IMXGPU_GBM:mx6-nxp-bsp      = ""
PACKAGECONFIG_IMXGPU_GBM:mx7-nxp-bsp      = ""
PACKAGECONFIG:imxgpu = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland',                     'wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', '${PACKAGECONFIG_IMXGPU_X11}', \
                                                                                  '', d), d)} \
    ${PACKAGECONFIG_IMXGPU_GBM} \
"
