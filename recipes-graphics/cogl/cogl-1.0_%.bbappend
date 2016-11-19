PACKAGECONFIG_mx6 ??= "cogl-pango gles2 \
                       ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'egl-x11', \
                                         bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                                                       'egl-wayland', '', d), d)}"
