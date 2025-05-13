# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia VPU wrapper"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=c0fb372b5d7f12181de23ef480f225f3"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = " \
    git://github.com/NXP/imx-vpuwrap.git;protocol=https;branch=${SRCBRANCH} \
    file://0001-vpu_wrapper_hantro_encoder-fix-missing-include.patch \
"
SRCBRANCH = "MM_04.09.03_2412_L6.12.y"
SRCREV = "d8703ee3245797111ad21c52982efa8b6db33241"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

CFLAGS += " -Wno-error=implicit-function-declaration"

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
