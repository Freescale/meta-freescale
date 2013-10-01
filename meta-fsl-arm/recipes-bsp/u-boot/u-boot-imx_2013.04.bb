# Copyright (C) 2013 Freescale Semiconductor

DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

# revision of 3.10.9-1.0.0 alpha branch
SRC_URI = "git://git.freescale.com/imx/uboot-imx.git;protocol=git;branch=imx_v2013.04"
SRCREV = "d20319c25d27c38fd531e0ea97b5661268449de6"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
