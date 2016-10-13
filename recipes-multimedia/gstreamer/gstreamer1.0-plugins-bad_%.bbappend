# Vivante EGL headers require the correct preprocessor
# defines to be set for each platform
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

CFLAGS_append_mx6 = " -DLINUX \
                      ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', \
                         bb.utils.contains('DISTRO_FEATURES', 'wayland', '-DEGL_API_FB -DWL_EGL_PLATFORM', '-DEGL_API_FB', d), d)}"

PACKAGECONFIG_GL_mx6sl = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', \
                           bb.utils.contains('DISTRO_FEATURES', 'x11', \
                                    'opengl', '', d), '', d)}"

SRC_URI_append = " file://0001-glplugin-Change-wayland-default-res-to-1024x768.patch \
                   file://0002-Add-directviv-to-glimagesink-to-improve-playback-per.patch \
                   file://0003-MMFMWK-6930-glplugin-Accelerate-gldownload-with-dire.patch \
                   file://0004-Fix-dependence-issue-between-gst-plugin-.patch \
"


PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
