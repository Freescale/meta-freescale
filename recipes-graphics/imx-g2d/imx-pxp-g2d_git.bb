# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "G2D library using i.MX PXP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2827219e81f28aba7c6a569f7c437fa7"

PROVIDES += "virtual/libg2d"

PV = "2.1.0+git${SRCPV}"

SRC_URI = "${IMX_PXP_G2D_SRC};branch=${SRCBRANCH}"
IMX_PXP_G2D_SRC ?= "git://github.com/nxp-imx/imx-g2d-pxp.git;protocol=https"
SRCBRANCH ?= "imx_2.1"
SRCREV = "f3adab05bd0cbb7c38578110d772e8ddf52b4e8e"

S = "${WORKDIR}/git"

inherit use-imx-headers

EXTRA_OEMAKE = "PLATFORM=IMX93 INCLUDE='-I${STAGING_INCDIR_IMX}' DEST_DIR=${D}"

do_install() {
    oe_runmake install
}

COMPATIBLE_MACHINE = "(mx93-nxp-bsp)"
