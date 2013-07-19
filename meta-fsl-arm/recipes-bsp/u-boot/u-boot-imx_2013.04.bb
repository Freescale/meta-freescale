# Copyright (C) 2013 Freescale Semiconductor

require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

# Revision of imx_v2013.04_3.5.7_1.0.0_alpha branch
SRC_URI = "git://git.freescale.com/imx/uboot-imx.git;protocol=git"
SRCREV = "a5a24c34364d59175d7d3130d02e8472572b9f4e"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
