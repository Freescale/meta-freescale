# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia VPU wrapper"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=ea25d099982d035af85d193c88a1b479"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = "git://github.com/NXP/imx-vpuwrap.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "MM_04.08.00_2305_L6.1.y"
SRCREV = "a9cd0835da0ecce55d94510d028371b473090fcc"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
