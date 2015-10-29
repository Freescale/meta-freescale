# Vivante EGL headers require the correct preprocessor
# defines to be set for each platform
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

CFLAGS_append_mx6 = " -DLINUX \
                      ${@base_contains('DISTRO_FEATURES', 'x11', '', \
                         base_contains('DISTRO_FEATURES', 'wayland', '-DEGL_API_FB -DWL_EGL_PLATFORM', '-DEGL_API_FB', d), d)}"

PACKAGECONFIG_GL_mx6sl = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', \
                           base_contains('DISTRO_FEATURES', 'x11', \
                                    'opengl', '', d), '', d)}"

SRC_URI_append_mx6 = " file://0001-PATCH-install-gstaggregator-and-gstvideoaggregator-h.patch"
SRC_URI_append_mx6ul = " file://0001-PATCH-install-gstaggregator-and-gstvideoaggregator-h.patch"
SRC_URI_append_mx7 = " file://0001-PATCH-install-gstaggregator-and-gstvideoaggregator-h.patch"


PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx6ul = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_SOCARCH}"
