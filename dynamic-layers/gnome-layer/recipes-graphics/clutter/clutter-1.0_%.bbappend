PACKAGECONFIG_imxgpu3d ??= " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland egl', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'glx x11', \
                                                       '', d), d)} \
"
