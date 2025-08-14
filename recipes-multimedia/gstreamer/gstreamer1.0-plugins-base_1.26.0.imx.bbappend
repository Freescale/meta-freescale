PACKAGECONFIG_GL:imxgpu2d = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'opengl x11', 'opengl', '', d)}"
PACKAGECONFIG_GL:imxgpu3d = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl', '', d)}"
PACKAGECONFIG_GL:use-mainline-bsp = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl gbm', '', d)}"

# The i.MX8 uses KMS instead of the Vivante specific framebuffer API.
# The i.MX7 does not have a GPU, except for ULP.
# This leaves the i.MX6 - with the vendor BSP - as the remaining use case for viv-fb.
#
# (Note that viv-fb is about the _windowing system_. Vivante direct texture support
# does not depend on the viv-fb feature. It used to, but that was actually a bug
# which was fixed in GStreamer 1.22.5. Since then, the direct texture support is
# detected by Meson by checking for direct texture symbols like "glTexDirectVIV".)
PACKAGECONFIG_GL:imxgpu2d:append:mx6-nxp-bsp = " viv-fb"
PACKAGECONFIG_GL:imxgpu2d:append:mx7-nxp-bsp = " viv-fb"
