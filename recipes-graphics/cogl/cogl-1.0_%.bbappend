PACKAGECONFIG_imxgpu2d ??= " \
    cogl-pango gles2 \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'egl-wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'egl-x11', \
       '', d), d)}"
