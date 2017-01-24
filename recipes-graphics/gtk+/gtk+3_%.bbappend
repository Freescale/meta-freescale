PACKAGECONFIG_remove_imxgpu2d = " \
    ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "x11", "", d)} \
"

CFLAGS_append_imxgpu2d = " \
    -DLINUX \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '-DEGL_API_FB -DEGL_API_WL', '', d)} \
"
