# Prepend path to override files from upstream recipe
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH:imx-generic-bsp = "${MACHINE_ARCH}"
PACKAGE_ARCH:qoriq-generic-bsp = "${MACHINE_ARCH}"
