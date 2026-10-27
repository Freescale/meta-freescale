# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target -- measured on qemuarm64 with bitbake -e,
# meta-freescale in and out of BBLAYERS.

# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
IMX_PATCH = "\
    file://0001-add-conf-for-multichannel-support-in-imx.patch \
    file://0005-add-ak4458-conf-for-multichannel-support.patch \
    file://0006-add-conf-for-iMX-XCVR-sound-card.patch \
    file://0007-add-conf-for-imx-cs42448-sound-card.patch \
    file://0008-add-conf-for-audmix-cs42448-in-aliases.conf.patch \
"
SRC_URI:append:imx-nxp-bsp = " ${IMX_PATCH}"

# The NXP BSP release for this branch (meta-imx wrynose-6.18.37-2.1.0) adds
# the 0008 patch from its own bbappend; listed twice, do_patch fails on the
# second copy. Keep one, so it applies once with or without that layer.
python __anonymous () {
    patch = "file://0008-add-conf-for-audmix-cs42448-in-aliases.conf.patch"
    src_uri = d.getVar("SRC_URI").split()
    if src_uri.count(patch) > 1:
        first = src_uri.index(patch) + 1
        d.setVar("SRC_URI", " ".join(src_uri[:first] + [u for u in src_uri[first:] if u != patch]))
}

PACKAGE_ARCH:imx-nxp-bsp = "${MACHINE_SOCARCH}"
