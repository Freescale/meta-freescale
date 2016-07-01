PACKAGECONFIG_imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland opengl', 'wayland-gles2', \
                                bb.utils.contains('DISTRO_FEATURES', 'x11 opengl',     'x11-gl x11-gles2', '', d), d)}"
PACKAGECONFIG_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland opengl', '', \
                                bb.utils.contains('DISTRO_FEATURES', 'x11 opengl',     'x11-gl', '', d), d)}"
