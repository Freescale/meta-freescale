# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target -- measured on qemuarm64 with bitbake -e,
# meta-freescale in and out of BBLAYERS.

# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:use-mainline-bsp = " file://0001-Allow-to-enable-atomic-in-modesetting-DDX.patch"

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
IMX_OPENGL_PKGCONFIGS_REMOVE = ""
IMX_OPENGL_PKGCONFIGS_REMOVE:imxgpu = "glamor"
OPENGL_PKGCONFIGS:remove:mx6-nxp-bsp = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS:remove:mx7-nxp-bsp = "${IMX_OPENGL_PKGCONFIGS_REMOVE}"
OPENGL_PKGCONFIGS:remove:imxdrm = "dri glx"
