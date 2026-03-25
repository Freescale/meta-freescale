# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2023,2025 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia VPU wrapper"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = "${IMX_VPUWRAP_SRC};branch=${SRCBRANCH}"
IMX_VPUWRAP_SRC ?= "git://github.com/NXP/imx-vpuwrap.git;protocol=https"
SRCBRANCH = "MM_04.10.03_2512_L6.18.2"
SRCREV = "1d2136e39789edb1eff730043caf2305ee2c173e"

inherit autotools pkgconfig

CFLAGS += " -Wno-error=implicit-function-declaration"

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
