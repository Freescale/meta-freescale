DEPENDS:append:imxgpu2d = " virtual/egl"

WAYLAND = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', 'x11', d)}"
WAYLANDONLY = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${WAYLAND}', '', d)}"

PACKAGECONFIG:remove:imxgpu2d = " ${WAYLANDONLY}"
