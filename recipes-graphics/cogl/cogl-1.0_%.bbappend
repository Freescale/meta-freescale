PACKAGECONFIG_imxgpu3d ??= " \
    cogl-pango gles2 \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'egl-wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'egl-x11', \
                                                       '', d), d)}"
