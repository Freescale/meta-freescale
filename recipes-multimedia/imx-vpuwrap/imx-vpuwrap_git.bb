# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia VPU wrapper"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=ca53281cc0caa7e320d4945a896fb837"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = " \
    git://github.com/NXP/imx-vpuwrap.git;protocol=https;branch=${SRCBRANCH} \
    file://0001-vpu_wrapper_hantro_encoder-add-sys-time.h-for-gettim.patch \
    file://0001-vpu_wrapper_hantro_VCencoder-add-sys-time.h-for-gett.patch \
"
SRCBRANCH = "MM_04.09.01_2408_L6.6.y"
SRCREV = "c13069d239cae314adc3651df25b96afa46cb434"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
