# Provide the need flags and backend settings to work with Vivante GPU
CFLAGS_append_mx6 = " -DLINUX \
                      ${@base_contains('DISTRO_FEATURES', 'x11', '-DEGL_API_FB', \
                                       base_contains('DISTRO_FEATURES', 'wayland', \
                                                     '-DEGL_API_WL', '', d), d)}"

PACKAGECONFIG_mx6 ??= "${base_contains('DISTRO_FEATURES', 'x11', 'glx x11', \
                                       base_contains('DISTRO_FEATURES', 'wayland', \
                                                     'wayland egl', '', d), d)}"
