# Copyright (C) 2011, 2012 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "AMD libz160 gpu driver"
LICENSE = "Proprietary"
SECTION = "libs"
PR = "r2"

#todo: Replace for correct AMD license
LIC_FILES_CHKSUM = "file://usr/include/z160.h;endline=28;md5=65dd44cd769091092f38e34cd52cc271"

SRC_URI = "${FSL_MIRROR}/libz160-bin-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "9a9c2c93f4b44e89316772d348eead7d"
SRC_URI[sha256sum] = "08767eb269a0a30ca0aa3d3b5aa9a53a2d17ed1c24651b7e8cefc7704b883f19"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -m 0755 ${S}/usr/lib/* ${D}${libdir}
    install -m 0644 ${S}/usr/include/* ${D}${includedir}
}

S = "${WORKDIR}/${PN}-bin-${PV}"

# Avoid QA Issue: No GNU_HASH in the elf binary
INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dev = "ldflags"
FILES_${PN} = "${libdir}/*.so"
FILES_${PN}-dev = "${includedir}"

COMPATIBLE_MACHINE = "(mx5)"
