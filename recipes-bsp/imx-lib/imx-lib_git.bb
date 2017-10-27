# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2012-2016 O.S. Systems Software LTDA.
# Copyright 2017 NXP

DESCRIPTION = "Platform specific libraries for imx platform"
LICENSE = "LGPLv2.1"
SECTION = "multimedia"
DEPENDS = "virtual/kernel"

LIC_FILES_CHKSUM = "file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

PE = "1"

PV = "5.8+${SRCPV}"

SRCBRANCH = "nxp/imx_4.9.11_1.0.0_ga"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-lib.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "f5f14fc24581e5d6e689f42a56b5f2992f978ef4"

S = "${WORKDIR}/git"

PLATFORM_mx6q  = "IMX6Q"
PLATFORM_mx6dl = "IMX6Q"
PLATFORM_mx6sl = "IMX6S"
PLATFORM_mx6sll = "IMX6UL"
PLATFORM_mx6sx = "IMX6S"
PLATFORM_mx6ul = "IMX6UL"
PLATFORM_mx7d  = "IMX7"
PLATFORM_mx7ulp = "IMX7"

PARALLEL_MAKE="-j 1"
EXTRA_OEMAKE = ""

do_compile () {
    INCLUDE_DIR="-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include"
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake PLATFORM="${PLATFORM}" DEST_DIR="${D}" install
}

COMPATIBLE_MACHINE = "(mx6|mx7)"
