# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia VPU wrapper"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=d3c315c6eaa43e07d8c130dc3a04a011"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = "git://github.com/NXP/imx-vpuwrap.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "MM_04.07.00_2205_L5.15.y"
SRCREV = "0e417b0ade3e55b5fb639d20c419b05aa31ab329"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
