# Copyright (C) 2011 Freescale Semicondutors <aalonso@freescale.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia codec libs"
LICENSE = "MIT"
SECTION = "multimedia"
PR = "r0"

LIC_FILES_CHKSUM = "file://ghdr/mp3_enc_interface.h;endline=11;md5=545a1927139b4739d8980c49954b6b95"

SRC_URI = "file://${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "6a15f85f4b48f0728e206c0709ef6b90"
SRC_URI[sha256sum] = "b4de979a2277e586094da78a8e4b81b74a6b33aea9850a2b88600c5144fdd0f1"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/mm_ghdr
    install -m 0755 ${S}/release/lib/*.so* ${D}${libdir}
    install -m 0644 ${S}/ghdr/*.h ${D}${includedir}/mm_ghdr
    install -m 0644 ${S}/pkgconfig/*.pc ${D}${libdir}/pkgconfig
}

FILES_${PN} += "${libdir}/*.so* ${libdir}/pkgconfig/*.pc"
FILES_${PN}-dev += "${includedir}/mm_ghdr/*.h"
