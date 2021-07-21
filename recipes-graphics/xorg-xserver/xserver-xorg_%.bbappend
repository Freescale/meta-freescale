FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_use-mainline-bsp = " file://0001-Allow-to-enable-atomic-in-modesetting-DDX.patch"
SRC_URI_append_imxgpu = " \
    file://0003-Remove-check-for-useSIGIO-option.patch \
    file://0001-MGS-5186-Per-Specification-EGL_NATIVE_PIXMAP_KHR-req.patch \
    file://0001-glamor-glamor_egl.c-EGL_NATIVE_PIXMAP_KHR-do-not-req.patch \
    file://0001-prefer-to-use-GLES2-for-glamor-EGL-config.patch \
"

IMX_OPENGL_PKGCONFIGS_REMOVE        = ""
IMX_OPENGL_PKGCONFIGS_REMOVE_imxgpu = "glamor"
OPENGL_PKGCONFIGS_remove_mx6        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS_remove_mx7        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS_remove_imxdrm     = "dri glx"
