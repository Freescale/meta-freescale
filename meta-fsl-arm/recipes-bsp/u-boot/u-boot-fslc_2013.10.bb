require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=6bc50ecab884fce80cd3ef3da8852b08"
COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6|vf60)"

DEPENDS_mxs += "elftosb-native"

PROVIDES += "u-boot"

PV = "v2013.10"

SRCREV = "aa822f4980039073e03f98998813061af833b2bf"
SRC_URI = "git://github.com/Freescale/u-boot-imx.git"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
