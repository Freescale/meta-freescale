DEPENDS:append:imxgpu2d = " virtual/egl"

PACKAGECONFIG:remove:imxgpu2d = " \
    ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "x11", "", d)} \
"
