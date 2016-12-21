# Vivante EGL headers require the correct preprocessor
# defines to be set for each platform
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG_GL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl x11', 'opengl', '', d)}"

PACKAGECONFIG_GL_imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', '', d)}"


SRC_URI_append_imxgpu2d = " \
    file://0001-glplugin-Change-wayland-default-res-to-1024x768.patch \
"


PACKAGE_ARCH_imxgpu2d = "${MACHINE_SOCARCH}"
