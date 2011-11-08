# Copyright (C) 2011 Freescale Semicondutors <aalonso@freescale.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia codec libs"
LICENSE = "MIT"
SECTION = "multimedia"
PR = "r0"

LIC_FILES_CHKSUM = "file://ghdr/common/fsl_types.h;endline=13;md5=b805ce4a390c94d002ef86bd15ceafd4"

SRC_URI = "file://${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "0aecb5f571d855fe194474a691704a1f"
SRC_URI[sha256sum] = "7774f9ff38d91400fe2557851180a82fd754daf0561b94ff5cc051cbea0618eb"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/mm_ghdr
    install -m 0755 ${S}/release/lib/*.so* ${D}${libdir}
    install -m 0644 ${S}/ghdr/common/*.h ${D}${includedir}/mm_ghdr
    install -m 0644 ${S}/pkgconfig/*.pc ${D}${libdir}/pkgconfig
}

FILES_${PN} += "${libdir}/*.so* ${libdir}/pkgconfig/*.pc"
FILES_${PN}-dev += "${includedir}/mm_ghdr/*.h"
