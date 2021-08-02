FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:use-mainline-bsp = " file://0001-Allow-to-enable-atomic-in-modesetting-DDX.patch"
SRC_URI:append:imxgpu = " \
    file://0003-Remove-check-for-useSIGIO-option.patch \
    file://0001-MGS-5186-Per-Specification-EGL_NATIVE_PIXMAP_KHR-req.patch \
    file://0001-glamor-glamor_egl.c-EGL_NATIVE_PIXMAP_KHR-do-not-req.patch \
    file://0001-prefer-to-use-GLES2-for-glamor-EGL-config.patch \
    file://0001-hw-xwayland-Makefile.am-fix-build-without-glx.patch \
"

IMX_OPENGL_PKGCONFIGS_REMOVE        = ""
IMX_OPENGL_PKGCONFIGS_REMOVE:imxgpu = "glamor"
OPENGL_PKGCONFIGS:remove:mx6        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS:remove:mx7        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS:remove_imxdrm     = "dri glx"
