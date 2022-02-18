# Append path for freescale layer to include alsa-state asound.conf
FILESEXTRAPATHS:prepend:mx6-nxp-bsp := "${THISDIR}/${PN}/imx-nxp-bsp"
FILESEXTRAPATHS:prepend:mx7-nxp-bsp := "${THISDIR}/${PN}/imx-nxp-bsp"
FILESEXTRAPATHS:prepend:mx8-nxp-bsp := "${THISDIR}/${PN}/imx-nxp-bsp"
FILESEXTRAPATHS:prepend:use-mainline-bsp := "${THISDIR}/${PN}/imx-nxp-bsp"

PACKAGE_ARCH:mx6-nxp-bsp = "${MACHINE_ARCH}"
PACKAGE_ARCH:mx7-nxp-bsp = "${MACHINE_ARCH}"
PACKAGE_ARCH:mx8-nxp-bsp = "${MACHINE_ARCH}"
PACKAGE_ARCH:use-mainline-bsp = "${MACHINE_ARCH}"
