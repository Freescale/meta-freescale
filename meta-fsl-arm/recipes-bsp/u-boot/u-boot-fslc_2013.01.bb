require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
COMPATIBLE_MACHINE = "(imx28evk|mx3|mx5|mx6)"

DEPENDS_mxs += "elftosb-native"

PROVIDES += "u-boot"

PV = "v2013.01"
PR = "r3"

SRCREV = "6997ad60ace1244119080bc1811586f231ef267a"
SRC_URI = "git://github.com/Freescale/u-boot-imx.git"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
