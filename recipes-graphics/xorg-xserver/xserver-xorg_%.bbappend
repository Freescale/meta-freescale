FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:use-mainline-bsp = " file://0001-Allow-to-enable-atomic-in-modesetting-DDX.patch"
SRC_URI:append:imxgpu = " \
    file://0003-Remove-check-for-useSIGIO-option.patch \
    file://0001-MGS-5186-Per-Specification-EGL_NATIVE_PIXMAP_KHR-req.patch \
    file://0001-glamor-glamor_egl.c-EGL_NATIVE_PIXMAP_KHR-do-not-req.patch \
    file://0001-prefer-to-use-GLES2-for-glamor-EGL-config.patch \
    file://0001-hw-xwayland-Makefile.am-fix-build-without-glx.patch \
    file://0001-xfree86-define-FOURCC_NV12-and-XVIMAGE_NV12.patch \
    file://0002-glamor-add-support-for-GL_RG.patch \
    file://0003-glamor-add-support-for-NV12-in-Xv.patch \
    file://0004-glamor-Remove-unused-format_for_pixmap-helper.patch \
    file://0005-glamor-Stop-trying-to-store-the-pixmap-s-format-in-g.patch \
    file://0006-glamor-Plumb-the-pixmap-through-fbo-creation-instead.patch \
    file://0007-glamor-Switch-the-gl_flavor-to-a-boolean-is_gles.patch \
    file://0008-glamor-Introduce-a-central-place-for-our-pixmap-form.patch \
"

IMX_OPENGL_PKGCONFIGS_REMOVE        = ""
IMX_OPENGL_PKGCONFIGS_REMOVE:imxgpu = "glamor"
OPENGL_PKGCONFIGS:remove:mx6-nxp-bsp        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS:remove:mx7-nxp-bsp        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS:remove:imxdrm     = "dri glx"
