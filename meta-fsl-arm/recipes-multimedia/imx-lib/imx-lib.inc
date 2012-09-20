# Copyright (C) 2012 Freescale Semiconductor

DESCRIPTION = "Platform specific libraries for imx platform"
LICENSE = "LGPLv2.1"
SECTION = "multimedia"
DEPENDS = "virtual/kernel"

INC_PR = "r2"

LIC_FILES_CHKSUM = "file://ipu/mxc_ipu_hl_lib.h;endline=13;md5=6c7486b21a8524b1879fa159578da31e"

SRC_URI = "file://${PN}-${PV}.tar.gz"

PLATFORM_mx5 = "IMX51"

PARALLEL_MAKE="-j 1"
EXTRA_OEMAKE = ""

do_compile () {
    INCLUDE_DIR="-I${STAGING_INCDIR} -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
                 -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include \
                 -I${STAGING_KERNEL_DIR}/include"
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake DEST_DIR="${D}" install
}

FILES_${PN} += "${libdir}/*${SOLIBS}"
FILES_${PN}-dbg += "${libdir}/.debug"
FILES_${PN}-dev += "${libdir}/*${SOLIBSDEV}"

