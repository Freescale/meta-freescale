CFLAGS_append_mx6 = " -DLINUX \
                      ${@base_contains('DISTRO_FEATURES', 'x11', '', \
                                        base_contains('DISTRO_FEATURES', 'wayland', \
                                                      '-DEGL_API_FB -DEGL_API_WL',  \
                                         base_contains('DISTRO_FEATURES', 'directfb', \
                                                       '-DEGL_API_DFB', '-DEGL_API_FB', d),d),d)}"


PACKAGECONFIG_class-target_mx6 = " \
    egl glesv2 \
    ${@base_contains('DISTRO_FEATURES', 'directfb', 'directfb', '', d)}"
