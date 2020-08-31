# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU G2D library and apps for i.MX with 2D GPU and DPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=228c72f2a91452b8a03c4cab30f30ef9"
PROVIDES += "virtual/libg2d"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "2b8fea58c87a724c1d5c023ee8bd551b"
SRC_URI[sha256sum] = "7533e34f447e8b076fa6461ac83806efdd677310094741eef2a67858034730fe"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp -r ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
    cp -r ${S}/gpu-demos/opt ${D}
}

FILES_${PN} = "${libdir}/libg2d* /opt"
FILES_${PN}-dev = "${libdir}/libg2d${SOLIBSDEV} ${includedir}"
INSANE_SKIP_${PN} += "ldflags"

RDEPENDS_${PN} = "libgal-imx libdrm"

# This is required to provide support for VPU Amphion HEVC tile format
# From NXP [MGS-5547] (commit e175d6b4f78deab24d319b852998bef55cdecc99):
# VPU Amphion HEVC tile support was added using OpenCL, so add a dependency on libopencl-imx.
RDEPENDS_${PN} += "libopencl-imx"

COMPATIBLE_MACHINE = "(imxdpu)"
