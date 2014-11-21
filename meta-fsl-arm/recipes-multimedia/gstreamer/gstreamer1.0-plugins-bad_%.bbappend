# Vivante EGL headers require the correct preprocessor
# defines to be set for each platform
CFLAGS_append_mx6 = " -DLINUX \
                      ${@base_contains('DISTRO_FEATURES', 'x11', '', \
                         base_contains('DISTRO_FEATURES', 'wayland', '-DEGL_API_FB -DWL_EGL_PLATFORM', \
                         base_contains('DISTRO_FEATURES', 'directfb', '-DEGL_API_DFB -I${STAGING_INCDIR}/directfb', \
                         '-DEGL_API_FB', d),d),d)}"

PACKAGECONFIG_GL_mx6sl = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', \
                           base_contains('DISTRO_FEATURES', 'x11', \
                                    'opengl', '', d), '', d)}"
