# Vivante EGL headers require the correct preprocessor
# defines to be set for each platform
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

CFLAGS_append_imxgpu2d = " -DLINUX ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', \
                            bb.utils.contains('DISTRO_FEATURES', 'wayland', '-DEGL_API_FB -DWL_EGL_PLATFORM', '-DEGL_API_FB', d), d)}"

PACKAGECONFIG_GL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl x11', 'opengl', '', d)}"

PACKAGECONFIG_GL_imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', '', d)}"


SRC_URI_append_imxgpu3d = " \
    file://0001-glplugin-Change-wayland-default-res-to-1024x768.patch \
    file://0002-Add-directviv-to-glimagesink-to-improve-playback-per.patch \
    file://0003-MMFMWK-6930-glplugin-Accelerate-gldownload-with-dire.patch \
    file://0004-Fix-dependence-issue-between-gst-plugin-.patch \
    file://0005-glcolorconvert-convert-YUV-to-RGB-use-directviv.patch \
    file://0006-glwindow-work-around-for-no-frame-when-imxplayer-use.patch \
    file://0007-glplugin-glcolorconvert-fix-MRT-cannot-work-in-GLES3.patch \
"


PACKAGE_ARCH_imxgpu2d = "${MACHINE_SOCARCH}"
