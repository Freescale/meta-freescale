# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target -- measured on qemuarm64 with bitbake -e,
# meta-freescale in and out of BBLAYERS.

# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
NXP_PATCHES = "\
    file://0001-unstable-Add-alpha-compositing-protocol.patch \
    file://0002-unstable-Add-hdr10-metadata-protocol.patch \
    file://0003-linux-dmabuf-support-passing-buffer-DTRC-meta-to-com.patch \
"

SRC_URI:append:imx-nxp-bsp:class-target = " ${NXP_PATCHES}"

# override the effect of "inherit allarch"
python allarch_package_arch_handler:prepend:imx-nxp-bsp:class-target () {
    return
}

PACKAGE_ARCH:imx-nxp-bsp:class-target = "${MACHINE_SOCARCH}"
