PACKAGECONFIG_X11                 = ""
PACKAGECONFIG_X11_append_imxgpu3d = " x11-egl glx"
PACKAGECONFIG_imxgpu = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'gbm wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', '${PACKAGECONFIG_X11}', \
                                                       '', d), d)} \
"
