# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.

DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"
DEPENDS_append = "dtc-145-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "imx_v2017.03_4.9.88_2.0.0_ga"
SRC_URI = "git://source.codeaurora.org/external/imx/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "b76bb1bf9fd21e21006d79552e28855ac43ad43c"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-${SRCBRANCH}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6|mx7)"
