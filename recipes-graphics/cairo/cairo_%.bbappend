CFLAGS_append_mx6 = " -DLINUX \
                      ${@base_contains('DISTRO_FEATURES', 'x11', '', \
                                        base_contains('DISTRO_FEATURES', 'wayland', \
                                                      '-DEGL_API_FB -DEGL_API_WL',  '-DEGL_API_FB', d), d)}"

PACKAGECONFIG_append_mx6q = " egl glesv2"
PACKAGECONFIG_append_mx6dl = " egl glesv2"
PACKAGECONFIG_append_mx6sx = " egl glesv2"
