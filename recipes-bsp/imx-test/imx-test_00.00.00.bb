SUMMARY = "Dummy package for SoCs lacking imx-test package"
DESCRIPTION = "Dummy package for SoCs lacking imx-test package"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

ALLOW_EMPTY:${PN} = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mxs-generic-bsp|mx5-generic-bsp|mx6-nxp-bsp|vf50-generic-bsp|vf60-generic-bsp)"
