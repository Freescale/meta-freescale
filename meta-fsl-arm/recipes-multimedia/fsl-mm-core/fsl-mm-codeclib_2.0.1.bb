# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale mm codec libs"
LICENSE = "MIT"
SECTION = "multimedia"
PR = "r0"

LIC_FILES_CHKSUM = "file://docs/EULA.txt;md5=ab04419c277bdd149cb4702b89ee31ff"

SRC_URI = "http://auslxsc01.mtwk.freescale.net/ppp/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "c4cd79a2a658aacbe8d574d6e4e5d56d"
SRC_URI[sha256sum] = "0ed15d1f246ec5c83a5b90672e52916dbfc5e683bd9358bdcbb08a30c8f76daf"

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

FILES_${PN} += "${libdir}/*.so"
