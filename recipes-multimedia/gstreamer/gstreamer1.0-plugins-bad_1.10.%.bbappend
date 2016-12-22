# Vivante EGL headers require the correct preprocessor
# defines to be set for each platform
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG_GL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl x11', 'opengl', '', d)}"

PACKAGECONFIG_GL_imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', '', d)}"


SRC_URI_append_imxgpu2d = " \
    file://0001-glplugin-Change-wayland-default-res-to-1024x768.patch \
    file://0002-Support-fb-backend-for-gl-plugins.patch \
    file://0003-Add-directviv-to-glimagesink-to-improve-playback-per.patch \
    file://0004-MMFMWK-6930-glplugin-Accelerate-gldownload-with.patch \
    file://0005-glcolorconvert-convert-YUV-to-RGB-use-directviv.patch \
    file://0006-glwindow-work-around-for-no-frame-when-imxplayer-use.patch \
    file://0007-glcolorconvert-fix-MRT-cannot-work-in-GLES3.0.patch \
"


PACKAGE_ARCH_imxgpu2d = "${MACHINE_SOCARCH}"
