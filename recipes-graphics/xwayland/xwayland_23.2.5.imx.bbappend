FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu = " \
    file://0001-Prefer-to-create-GLES2-context-for-glamor-EGL.patch \
"

OPENGL_PKGCONFIGS:remove:imxgpu = "${OPENGL_PKGCONFIGS_REMOVE_IMXGPU}"
OPENGL_PKGCONFIGS_REMOVE_IMXGPU             = ""
OPENGL_PKGCONFIGS_REMOVE_IMXGPU:imx-nxp-bsp = "glamor glx"
OPENGL_PKGCONFIGS_REMOVE_IMXGPU:mx8-nxp-bsp = "glx"

# links with imx-gpu libs which are pre-built for glibc
# gcompat will address it during runtime
LDFLAGS:append:imxgpu:libc-musl = " -Wl,--allow-shlib-undefined"

RDEPENDS:${PN}:append:imxgpu:libc-musl = " gcompat"

PACKAGE_ARCH:imxgpu = "${MACHINE_SOCARCH}"
