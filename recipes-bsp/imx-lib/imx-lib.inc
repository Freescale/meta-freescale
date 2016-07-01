# Copyright (C) 2012-2013 Freescale Semiconductor
# Copyright (C) 2012-2016 O.S. Systems Software LTDA.

DESCRIPTION = "Platform specific libraries for imx platform"
LICENSE = "LGPLv2.1"
SECTION = "multimedia"
DEPENDS = "virtual/kernel"

LIC_FILES_CHKSUM = "file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

PLATFORM_mx6q  = "IMX6Q"
PLATFORM_mx6dl = "IMX6Q"
PLATFORM_mx6sl = "IMX6S"
PLATFORM_mx6sx = "IMX6S"
PLATFORM_mx7d  = "IMX7"
PLATFORM_mx6ul = "IMX6UL"

PARALLEL_MAKE="-j 1"
EXTRA_OEMAKE = ""

SRC_URI = "${FSL_MIRROR}/imx-lib-${PV}.tar.gz"

do_compile () {
    INCLUDE_DIR="-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include \
                 -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
                 -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include"

    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake PLATFORM="${PLATFORM}" DEST_DIR="${D}" install
}
