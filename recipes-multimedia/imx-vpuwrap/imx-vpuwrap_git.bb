# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2023,2025 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia VPU wrapper"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

DEPENDS = "virtual/imxvpu"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = "git://github.com/NXP/imx-vpuwrap.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "MM_04.10.0_2505_L6.12.20"
SRCREV = "feeb9109a027d3a0f040b0bddb5f79a1daad3587"

inherit autotools pkgconfig

CFLAGS += " -Wno-error=implicit-function-declaration"

do_install:append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(imxvpu)"
