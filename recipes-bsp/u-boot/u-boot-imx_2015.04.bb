# Copyright (C) 2013-2015 Freescale Semiconductor

DESCRIPTION = "U-Boot provided by Freescale with focus on  i.MX reference boards."
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH_mx6 = "imx_v2015.04_3.14.38_6qp_ga"
SRCREV_mx6 = "f80d628744bd1e669e365c52068067563b577e16"

SRCBRANCH_mx6ul = "imx_v2015.04_3.14.38_6ul_ga"
SRCREV_mx6ul = "5d63276bfebc82cffa10d66ddaa903dd2ab0df43"

SRCBRANCH_mx7 = "imx_v2015.04_3.14.38_6ul_ga"
SRCREV_mx7 = "5d63276bfebc82cffa10d66ddaa903dd2ab0df43"

SRC_URI = "git://git.freescale.com/imx/uboot-imx.git;protocol=git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-${SRCBRANCH}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
