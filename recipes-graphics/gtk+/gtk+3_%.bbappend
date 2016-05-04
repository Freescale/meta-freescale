PACKAGECONFIG_remove_mx6 = "${@bb.utils.contains("DISTRO_FEATURES", "x11", "wayland", "", d)}"

CFLAGS_append_mx6 = " -DLINUX \
                      ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', \
                                        bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                                                      '-DEGL_API_FB -DEGL_API_WL',  '', d), d)}"
