CFLAGS_append_imxgpu2d = " \
    -DLINUX \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', \
                          '', \
                          bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                                            '-DEGL_API_FB -DEGL_API_WL', \
                                            '-DEGL_API_FB', d), d)} \
"

PACKAGECONFIG_append_imxgpu3d = " egl glesv2"
