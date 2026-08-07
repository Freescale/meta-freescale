# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target -- measured on qemuarm64 with bitbake -e,
# meta-freescale in and out of BBLAYERS.

# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu = " \
    file://0001-Prefer-to-create-GLES2-context-for-glamor-EGL.patch \
"

OPENGL_PKGCONFIGS:remove:imxgpu = "${OPENGL_PKGCONFIGS_REMOVE_IMXGPU}"
# Base default specialized by the machine overrides below.
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
OPENGL_PKGCONFIGS_REMOVE_IMXGPU = ""
OPENGL_PKGCONFIGS_REMOVE_IMXGPU:imx-nxp-bsp = "glamor glx"
OPENGL_PKGCONFIGS_REMOVE_IMXGPU:mx8-nxp-bsp = "glx"

PACKAGE_ARCH:imxgpu = "${MACHINE_SOCARCH}"

# links with imx-gpu libs which are pre-built for glibc
# gcompat will address it during runtime
LDFLAGS:append:imxgpu:libc-musl = " -Wl,--allow-shlib-undefined"

RDEPENDS:${PN}:append:imxgpu:libc-musl = " gcompat"
