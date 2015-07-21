PACKAGECONFIG_remove_mx6q  = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'wayland-gl wayland-gles2', '', d)}"
PACKAGECONFIG_remove_mx6dl = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'wayland-gl wayland-gles2', '', d)}"
PACKAGECONFIG_remove_mx6sx = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'wayland-gl wayland-gles2', '', d)}"
PACKAGECONFIG_remove_mx6sl = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'wayland-gl wayland-gles2', '', d)} \
                              drm-gl x11-gles2 drm-gles2"
