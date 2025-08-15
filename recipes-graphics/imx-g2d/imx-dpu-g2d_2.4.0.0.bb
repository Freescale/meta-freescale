# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2025 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "G2D library using i.MX DPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359" 

DEPENDS = "libdrm ${LIBGAL_IMX}"
LIBGAL_IMX        = ""
LIBGAL_IMX:imxviv = "libgal-imx"

PROVIDES += "virtual/libg2d"

SRC_URI = "${FSL_MIRROR}/${IMX_BIN_NAME}.bin;fsl-eula=true;name=${IMX_SRC_URI_NAME}"
IMX_BIN_NAME = "${BPN}-${IMX_SRC_URI_NAME}-${PV}-${IMX_SRCREV_ABBREV}"

IMX_SRCREV_ABBREV = "9e8bbf1"
IMX_SRC_URI_NAME = "v1"
SRC_URI[v1.sha256sum] = "67be9b56a5d2196613460d3cc3a734d7a088e2177377e27f83287766d5d8cf70"

IMX_SRCREV_ABBREV:imxmali = "9e8bbf1"
IMX_SRC_URI_NAME:imxmali = "v2"
SRC_URI[v2.sha256sum] = "f56dae607f89e0f414625657d8345c5d1b68b79d3ac61e50016fbe4d49a91402"

S = "${WORKDIR}/${IMX_BIN_NAME}"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp -d ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
}

INSANE_SKIP:append:libc-musl = " file-rdeps"
RDEPENDS:${PN}:append:libc-musl = " gcompat"

# The packaged binaries have been stripped of debug info, so disable
# operations accordingly.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imxdpu)"
