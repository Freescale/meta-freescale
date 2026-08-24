PACKAGECONFIG:append:imx-nxp-bsp = " ${PACKAGECONFIG_LIBDECOR}"
# Fallback default for the machine overrides below; it cannot carry an
# override for the machines it is the fallback for, and every consumer of it
# is override-scoped, so it is inert off-target.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_LIBDECOR ??= "libdecor"

# what vivante driver does libsdl2 mean? Anyway it fails with missing functions as
# VIVANTE_Create VIVANTE_GLES_GetProcAddress VIVANTE_GLES_UnloadLibrary ...
EXTRA_OECMAKE:append:imxgpu = " -DSDL_VIVANTE=OFF"

CFLAGS:append:imxgpu = " \
    -DLINUX \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', '-DEGL_API_FB', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '-DWL_EGL_PLATFORM', '', d)} \
"
