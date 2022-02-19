FILESEXTRAPATHS:prepend:imx-generic-bsp := "${THISDIR}/${PN}/imx-generic-bsp:"

# Append path for freescale layer
PACKAGE_ARCH:imx-generic-bsp = "${MACHINE_ARCH}"
