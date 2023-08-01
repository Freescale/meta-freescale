FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:use-mainline-bsp = " file://0001-Allow-to-enable-atomic-in-modesetting-DDX.patch"

SRC_URI:append:imxgpu = " file://0001-glamor-Fix-fbo-pixmap-format-with-GL_BGRA_EXT.patch"

IMX_OPENGL_PKGCONFIGS_REMOVE        = ""
IMX_OPENGL_PKGCONFIGS_REMOVE:imxgpu = "glamor"
OPENGL_PKGCONFIGS:remove:mx6-nxp-bsp        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS:remove:mx7-nxp-bsp        = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS:remove:imxdrm     = "dri glx"
