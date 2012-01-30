# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Platform specific libraries for imx platform"
LICENSE = "LGPL"
SECTION = "multimedia"
DEPENDS = "virtual/kernel"
PR = "r1"

LIC_FILES_CHKSUM = "file://ipu/mxc_ipu_hl_lib.h;endline=13;md5=6c7486b21a8524b1879fa159578da31e"

SRC_URI = "file://${PN}-${PV}.tar.gz \
           file://imx-lib-remove-shared-libs-symlinks.patch \
           file://0001-ENGR00156800-vpu-Fix-decoding-mp4PackedPBFrame-strea.patch \
           file://0002-ENGR00162690-vpu-Fix-the-issue-of-rotation-180-degre.patch"
SRC_URI[md5sum] = "45574f8f32f7000ca11d585fa60dea8c"
SRC_URI[sha256sum] = "f151a8bb3099b596b5834a1139c19e526802e6a0aa965018d16375e7e1f48f27"

# override parallel make flags
PARALLEL_MAKE="-j 1"
EXTRA_OEMAKE = ""

do_compile () {
    INCLUDE_DIR="-I${STAGING_INCDIR} -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
                 -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include \
                 -I${STAGING_KERNEL_DIR}/include"
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="IMX51" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake DEST_DIR="${D}" install
}

FILES_${PN} += "${libdir}/*.so"
FILES_${PN}-dbg += "${libdir}/.debug"
FILES_${PN}-dev += "${libdir}/*.la ${libdir}/*.a"
