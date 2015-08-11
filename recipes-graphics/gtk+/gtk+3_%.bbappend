PACKAGECONFIG_remove_mx6 = "${@base_contains("DISTRO_FEATURES", "x11", "wayland", "", d)}"

CFLAGS_append_mx6 = " -DLINUX \
                      ${@base_contains('DISTRO_FEATURES', 'x11', '', \
                                        base_contains('DISTRO_FEATURES', 'wayland', \
                                                      '-DEGL_API_FB -DEGL_API_WL',  '', d), d)}"
