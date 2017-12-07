DEPENDS_append_imxgpu2d = " virtual/egl"

PACKAGECONFIG_remove_imxgpu2d = " \
    ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "x11", "", d)} \
"
