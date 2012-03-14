require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
COMPATIBLE_MACHINE = "(imx53qsb|imx53ard|imx51evk)"

DEPENDS_mxs += "elftosb-native"

SRCREV = "6751b05f855bbe56005d5b88d4eb58bcd52170d2"

PV = "v2011.12+git"

SRC_URI = "git://git.denx.de/u-boot.git;branch=master;protocol=git"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
