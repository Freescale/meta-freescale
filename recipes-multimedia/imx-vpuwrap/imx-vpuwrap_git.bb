# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2023,2025-2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX VPU wrapper library"
DESCRIPTION = "Freescale Multimedia VPU wrapper"
HOMEPAGE = "https://github.com/NXP/imx-vpuwrap"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=6f862c6751ebcaa393467694c7b0c69a"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = "${IMX_VPUWRAP_SRC};branch=${SRCBRANCH}"
IMX_VPUWRAP_SRC ?= "git://github.com/NXP/imx-vpuwrap.git;protocol=https"
SRCBRANCH = "MM_04.11.00_2605_L6.18.20"
SRCREV = "49adeca51746d6b3f89a32687c98df61a56a52ae"

inherit autotools pkgconfig

CFLAGS += "-Wno-error=implicit-function-declaration"

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
