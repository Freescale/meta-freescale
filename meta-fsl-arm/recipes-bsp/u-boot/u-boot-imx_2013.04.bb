# Copyright (C) 2013, 2014 Freescale Semiconductor

DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

SRCBRANCH = "imx_v2013.04_3.10.17_1.0.0_beta"
SRC_URI = "git://git.freescale.com/imx/uboot-imx.git;protocol=git;branch=${SRCBRANCH}"
SRCREV = "f007d717738dad99a16dc540bcbeacff5f17e405"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
