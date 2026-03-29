# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "G2D library using i.MX PXP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=a93b654673e1bc8398ed1f30e0813359"

PROVIDES += "virtual/libg2d"

PV = "2.5.0+git"

SRC_URI = "${IMX_PXP_G2D_SRC};branch=${SRCBRANCH}"
IMX_PXP_G2D_SRC ?= "git://github.com/nxp-imx/imx-g2d-pxp.git;protocol=https"
SRCBRANCH = "imx_2.5"
SRCREV = "7dd4ca631b894a29389dadda5bd112cfa576b2d9"

inherit use-imx-headers

EXTRA_OEMAKE = "PLATFORM=${IMX_PLATFORM} INCLUDE='-I${STAGING_INCDIR_IMX}' DEST_DIR=${D}"
IMX_PLATFORM:mx93-nxp-bsp  = "IMX93"
IMX_PLATFORM:mx943-nxp-bsp = "IMX943"

do_install() {
    oe_runmake install
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx93-nxp-bsp|mx943-nxp-bsp)"
