# Prepend path to override files from upstream recipe
FILESEXTRAPATHS:prepend:imx-generic-bsp := "${THISDIR}/${PN}/imx-generic-bsp:"

PACKAGE_ARCH:imx-generic-bsp = "${MACHINE_ARCH}"
