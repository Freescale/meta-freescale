PACKAGECONFIG_GL:imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl x11', 'opengl', '', d)}"
PACKAGECONFIG_GL:imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl', '', d)}"

# For mainline BSP we need to enable 'gbm' Window system
PACKAGECONFIG_GL:use-mainline-bsp = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl gbm', '', d)}"
