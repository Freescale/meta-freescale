FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

IMX_PATCH = " \
    file://0001-add-conf-for-multichannel-support-in-imx.patch \
    file://0005-add-ak4458-conf-for-multichannel-support.patch \
    file://0006-add-conf-for-iMX-XCVR-sound-card.patch \
"
SRC_URI:append:imx-nxp-bsp = "${IMX_PATCH}"

PACKAGE_ARCH:imx-nxp-bsp = "${MACHINE_SOCARCH}"
