# Copyright (C) 2011, 2012 Freescale Semicondutors <aalonso@freescale.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia codec libs"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://ghdr/mp3_enc_interface.h;endline=11;md5=545a1927139b4739d8980c49954b6b95"

INC_PR = "r1"

SRC_URI = "${FSL_MIRROR}/fsl-mm-mp3enc-codeclib-${PV}.bin;fsl-eula=true"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/mm_ghdr
    install -m 0755 ${S}/release/lib/*.so* ${D}${libdir}
    install -m 0644 ${S}/ghdr/*.h ${D}${includedir}/mm_ghdr
    install -m 0644 ${S}/pkgconfig/*.pc ${D}${libdir}/pkgconfig
}

FILES_${PN} += "${libdir}/*.so* ${libdir}/pkgconfig/*.pc"
INSANE_SKIP_${PN} = "ldflags"

FILES_${PN}-dev += "${includedir}/mm_ghdr/*.h"
INSANE_SKIP_${PN}-dev = "ldflags"
