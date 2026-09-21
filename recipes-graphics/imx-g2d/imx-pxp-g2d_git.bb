# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX PXP G2D library"
DESCRIPTION = "G2D library using i.MX PXP"
HOMEPAGE = "https://github.com/nxp-imx/imx-g2d-pxp"
SECTION = "graphics"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=0a977e480eb69a215e364c381ff61aec"

PROVIDES += "virtual/libg2d"

PV = "2.5.0+git"

SRC_URI = "${IMX_PXP_G2D_SRC};branch=${SRCBRANCH}"
IMX_PXP_G2D_SRC ?= "git://github.com/nxp-imx/imx-g2d-pxp.git;protocol=https"
SRCBRANCH = "imx_2.5"
SRCREV = "9551dd7f97c37af80b42cd8845ffb5aa190145dc"

inherit use-imx-headers

EXTRA_OEMAKE = "PLATFORM=${IMX_PLATFORM} INCLUDE='-I${STAGING_INCDIR_IMX}' DEST_DIR=${D}"
IMX_PLATFORM:mx93-nxp-bsp = "IMX93"
IMX_PLATFORM:mx943-nxp-bsp = "IMX943"

do_install() {
    oe_runmake install
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx93-nxp-bsp|mx943-nxp-bsp)"
