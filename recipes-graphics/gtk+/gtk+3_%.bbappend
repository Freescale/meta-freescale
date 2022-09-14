DEPENDS:append:imxgpu2d = " virtual/egl"

WAYLAND = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', 'wayland', d)}"
WAYLANDONLY = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${WAYLAND}', '', d)}"

PACKAGECONFIG:remove:imxgpu2d = " ${WAYLANDONLY}"
