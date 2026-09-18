# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2026 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX GPU G2D library"
DESCRIPTION = "G2D library using i.MX GPU"
HOMEPAGE = "https://github.com/nxp-imx/imx-gpu-g2d"
SECTION = "graphics"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=0858ec9c7a80c4a2cf16e4f825a2cc91"

# The Vivante GPU driver provides libGAL, the HAL headers and, where
# available, the OpenCL headers and library used for color space conversion.
DEPENDS = "libgal-imx"

PROVIDES += "virtual/libg2d"

PV = "2.5.0+git"

SRC_URI = "${IMX_GPU_G2D_SRC};branch=${SRCBRANCH}"
IMX_GPU_G2D_SRC ?= "git://github.com/nxp-imx/imx-gpu-g2d.git;protocol=https"
SRCBRANCH = "imx_2.5"
SRCREV = "d494271fcc8759c04467ec778c17ed5cb22141da"

S = "${UNPACKDIR}/${BP}/source"

inherit use-imx-headers

EXTRA_OEMAKE = "\
    ARCH=${ARCH} \
    ROOTFS_USR=${STAGING_DIR_HOST}${prefix} \
    USE_OPENCL=${USE_OPENCL} \
"
ARCH = ""
ARCH:aarch64 = "arm64-yocto"

USE_OPENCL = "1"
USE_OPENCL:arm = "0"
USE_OPENCL:mx8mm-nxp-bsp = "0"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp -d --no-preserve=ownership ${S}/../lib/lib*.so* ${D}${libdir}
    cp -d --no-preserve=ownership ${S}/../include/* ${D}${includedir}
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(imxgpu2d)"
