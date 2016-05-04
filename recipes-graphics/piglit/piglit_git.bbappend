DEPENDS_append_mx6 = " virtual/egl"

# Provide the need flags and backend settings to work with Vivante GPU
CFLAGS_append_mx6 = " -DLINUX \
                      ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '-DEGL_API_FB', \
                                        bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                                                      '-DEGL_API_WL', '', d), d)}"
