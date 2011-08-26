# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Platform specific libraries for imx platform"
LICENSE = "LGPL"
SECTION = "multimedia"
PR = "r1"

LIC_FILES_CHKSUM = "file://ipu/mxc_ipu_hl_lib.h;endline=13;md5=6c7486b21a8524b1879fa159578da31e"

SRC_URI = "http://auslxsc01.mtwk.freescale.net/ppp/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "67e1be286abbee3529fc55cdf2b4cd51"
SRC_URI[sha256sum] = "063e722e8abc6f2e90afde1338f213b92bc1187a7a741ec04df22851cc952e29"

# override parallel make flags
PARALLEL_MAKE="-j 1"
EXTRA_OEMAKE = ""

do_compile () {
    INCLUDE_DIR="-I${STAGING_INCDIR} -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
                 -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include"
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="IMX51" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake DEST_DIR="${D}" install
}

FILES_${PN} += "${libdir}/*.so"
FILES_${PN}-dbg  += "${libdir}/.debug"
FILES_${PN}-dev  += "${libdir}/*.la ${libdir}/*.a"
