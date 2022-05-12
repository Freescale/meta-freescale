FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu = " \
    file://0001-Prefer-to-create-GLES2-context-for-glamor-EGL.patch \
    file://0002-glamor-Fix-fbo-pixmap-format-with-GL_BGRA_EXT.patch \
"

OPENGL_PKGCONFIGS:remove:imxgpu = "${OPENGL_PKGCONFIGS_REMOVE_IMXGPU}"
OPENGL_PKGCONFIGS_REMOVE_IMXGPU             = ""
OPENGL_PKGCONFIGS_REMOVE_IMXGPU:mx6-nxp-bsp = "glamor glx"
OPENGL_PKGCONFIGS_REMOVE_IMXGPU:mx7-nxp-bsp = "glamor glx"
OPENGL_PKGCONFIGS_REMOVE_IMXGPU:mx8-nxp-bsp = "glx"
