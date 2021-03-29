FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append_imxgpu = " \
    file://0003-Remove-check-for-useSIGIO-option.patch \
    file://0001-hw-xwayland-Makefile.am-fix-build-without-glx.patch \
"

IMX_OPENGL_PKGCONFIGS_REMOVE        = ""
IMX_OPENGL_PKGCONFIGS_REMOVE_imxgpu = "glamor"
OPENGL_PKGCONFIGS_remove_mx6        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS_remove_mx7        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS_remove_mx8        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS_remove_imxdrm     = "dri glx"
