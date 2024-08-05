# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia VPU wrapper"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = " \
    git://github.com/NXP/imx-vpuwrap.git;protocol=https;branch=${SRCBRANCH} \
    file://0001-vpu_wrapper_hantro_encoder-add-sys-time.h-for-gettim.patch \
    file://0001-vpu_wrapper_hantro_VCencoder-add-sys-time.h-for-gett.patch \
"
SRCBRANCH = "MM_04.09.00_2405_L6.6.y"
SRCREV = "73093da30dc4053c9f69813a6447091bfca5429b"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
