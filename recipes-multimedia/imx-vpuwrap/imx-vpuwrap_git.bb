# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia VPU wrapper"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=2827219e81f28aba7c6a569f7c437fa7"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = " \
    git://github.com/NXP/imx-vpuwrap.git;protocol=https;branch=${SRCBRANCH} \
    file://0001-vpu_wrapper_hantro_encoder-add-sys-time.h-for-gettim.patch \
"
SRCBRANCH = "MM_04.08.03_2312_L6.6.y"
SRCREV = "f974cecdb00b4a214e4b5229f2279e772ee43306"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
