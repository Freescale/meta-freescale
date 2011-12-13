# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"
#DEPENDS = "elftosb-native"
PR = "r1"

PV = "v2009.08"
SRC_URI = "git://sw-git.freescale.net/FPS/uboot-imx.git;branch=imx_v2009.08_10.07.11"
S = "${WORKDIR}/git"
