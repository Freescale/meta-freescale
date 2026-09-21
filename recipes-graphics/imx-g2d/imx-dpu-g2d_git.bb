# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX DPU G2D library"
DESCRIPTION = "G2D library using i.MX DPU"
HOMEPAGE = "https://github.com/nxp-imx/imx-dpu-g2d"
SECTION = "graphics"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=0858ec9c7a80c4a2cf16e4f825a2cc91"

DEPENDS = "libdrm"
# DPU v1 (i.MX 8QM/8QXP) uses OpenCL from the Vivante GPU driver for
# color space conversion.
DEPENDS:append:imxviv = " opencl-headers"

PROVIDES += "virtual/libg2d"

PV = "2.5.0+git"

SRC_URI = "\
    ${IMX_DPU_G2D_SRC};branch=${SRCBRANCH} \
    file://0001-g2d-Remove-unused-totalUsed-variable.patch;patchdir=.. \
"
IMX_DPU_G2D_SRC ?= "git://github.com/nxp-imx/imx-dpu-g2d.git;protocol=https"
SRCBRANCH = "imx_2.5"
SRCREV = "d9b70208ff7481406d27798fcb54c20a3eea26ad"

S = "${UNPACKDIR}/${BP}/source"

inherit use-imx-headers pkgconfig

EXTRA_OEMAKE:imxmali = "BUILD_DPU_VERSION=2"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp -d --no-preserve=ownership ${S}/../lib/lib*.so* ${D}${libdir}
    cp -d --no-preserve=ownership ${S}/../include/* ${D}${includedir}
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

# musl needs gcompat for the glibc-built prebuilt GPU libraries this links
# against; their runtime deps can't be mapped by QA.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:append:libc-musl = " file-rdeps"
RDEPENDS:${PN}:append:libc-musl = " gcompat"

COMPATIBLE_MACHINE = "(imxdpu)"
