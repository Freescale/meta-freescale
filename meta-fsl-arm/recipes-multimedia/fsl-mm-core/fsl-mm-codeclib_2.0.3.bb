# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale mm codec libs"
LICENSE = "MIT"
SECTION = "multimedia"
DEPENDS = "pkgconfig"
PR = "r0"

LIC_FILES_CHKSUM = "file://docs/EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

SRC_URI = "file://${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "e06bcaae6a7f5162caaa4ae5a56bf067"
SRC_URI[sha256sum] = "5f1ec8200469d886dfbe82a22a63033914dc690c545c74a27ababb24fd53952a"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/mm_ghdr
    install -d ${D}${includedir}/mm_ghdr/aac_parser
    install -d ${D}${includedir}/mm_ghdr/common
    install -d ${D}${includedir}/mm_ghdr/flac_parser
    install -d ${D}${includedir}/mm_ghdr/mp3_parser
    install -d ${D}${includedir}/mm_ghdr/mp3_parser_v2
    install -d ${D}${includedir}/mm_ghdr/sbc
    install -d ${D}${includedir}/mm_ghdr/wav_parser
    install -m 0755 ${S}/release/lib/* ${D}${libdir}
    install -m 0644 ${S}/pkgconfig/fsl-mm-core.pc ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/ghdr/*.h ${D}${includedir}/mm_ghdr
    install -m 0644 ${S}/ghdr/aac_parser/*.h ${D}${includedir}/mm_ghdr/aac_parser
    install -m 0644 ${S}/ghdr/common/*.h ${D}${includedir}/mm_ghdr/common
    install -m 0644 ${S}/ghdr/flac_parser/*.h ${D}${includedir}/mm_ghdr/flac_parser
    install -m 0644 ${S}/ghdr/mp3_parser/*.h ${D}${includedir}/mm_ghdr/mp3_parser
    install -m 0644 ${S}/ghdr/mp3_parser_v2/*.h ${D}${includedir}/mm_ghdr/mp3_parser_v2
    install -m 0644 ${S}/ghdr/sbc/*.h ${D}${includedir}/mm_ghdr/sbc
    install -m 0644 ${S}/ghdr/wav_parser/*.h ${D}${includedir}/mm_ghdr/wav_parser
}

INSANE_SKIP = "True"
FILES_${PN} += "${libdir}/*.so ${libdir}/pkgconfig/*.pc"
FILES_${PN}-dev += "${includedir}/mm_ghdr/*.h \
    ${includedir}/mm_ghdr/aac_parser/*.h \
    ${includedir}/mm_ghdr/common/*.h \
    ${includedir}/mm_ghdr/flac_parser/*.h \
    ${includedir}/mm_ghdr/mp3_parser/*.h \
    ${includedir}/mm_ghdr/mp3_parser2/*.h \
    ${includedir}/mm_ghdr/sbc/*.h \
    ${includedir}/mm_ghdr/wav_parser/*.h"
