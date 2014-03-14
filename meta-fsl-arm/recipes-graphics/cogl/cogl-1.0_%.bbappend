# Provide the need flags and backend settings to work with Vivante GPU
CFLAGS_append_mx6 = " -DLINUX \
                      ${@base_contains('DISTRO_FEATURES', 'x11', '', \
                                        base_contains('DISTRO_FEATURES', 'wayland', \
                                                      '-DEGL_API_WL -DEGL_API_FB', '', d), d)}"

PACKAGECONFIG_mx6 ??= "cogl-pango gles2 \
                       ${@base_contains('DISTRO_FEATURES', 'x11', 'egl-x11', \
                                         base_contains('DISTRO_FEATURES', 'wayland', \
                                                       'egl-wayland', '', d), d)}"
