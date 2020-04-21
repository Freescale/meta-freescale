PACKAGECONFIG_IMXGPU_X11          = ""
PACKAGECONFIG_IMXGPU_X11_imxgpu3d = "x11-egl glx"
PACKAGECONFIG_IMXGPU_GBM          = "gbm"
PACKAGECONFIG_IMXGPU_GBM_mx6      = ""
PACKAGECONFIG_IMXGPU_GBM_mx7      = ""
PACKAGECONFIG_imxgpu = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland',                     'wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', '${PACKAGECONFIG_IMXGPU_X11}', \
                                                                                  '', d), d)} \
    ${PACKAGECONFIG_IMXGPU_GBM} \
"
