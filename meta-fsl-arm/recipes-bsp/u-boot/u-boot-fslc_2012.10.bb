require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
COMPATIBLE_MACHINE = "(imx28evk|mx3|mx5|mx6)"

DEPENDS_mxs += "elftosb-native"

PROVIDES += "u-boot"

PV = "v2012.10"
PR = "r2"

SRCREV = "8f78e9e9bd4c2471fdd03644058aed3038dcbf38"
SRC_URI = "git://github.com/Freescale/u-boot-imx.git"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
