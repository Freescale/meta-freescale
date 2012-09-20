# Copyright (C) 2011, 2012 Freescale Semicondutors <aalonso@freescale.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia codec libs"
LICENSE = "Proprietary"
SECTION = "multimedia"

PR = "r1"

LIC_FILES_CHKSUM = "file://ghdr/common/fsl_types.h;endline=13;md5=b805ce4a390c94d002ef86bd15ceafd4"

SRC_URI = "${FSL_MIRROR}/fsl-mm-flv-codeclib-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "b0244d881f73557be9221e6534c239be"
SRC_URI[sha256sum] = "737399c76e8b991ebb76c07599b83da6a185211d88b95d9a9b4245f6d7fccec2"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/mm_ghdr
    install -m 0755 ${S}/release/lib/*.so* ${D}${libdir}
    install -m 0644 ${S}/ghdr/common/*.h ${D}${includedir}/mm_ghdr
    install -m 0644 ${S}/pkgconfig/*.pc ${D}${libdir}/pkgconfig
}

FILES_${PN} += "${libdir}/*.so* ${libdir}/pkgconfig/*.pc"
INSANE_SKIP_${PN} = "ldflags"

FILES_${PN}-dev += "${includedir}/mm_ghdr/*.h"
INSANE_SKIP_${PN}-dev = "ldflags"
