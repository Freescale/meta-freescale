# Copyright (C) 2011, 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale mm codec libs"
SECTION = "multimedia"
LICENSE = "Proprietary"
DEPENDS = "pkgconfig"

LIC_FILES_CHKSUM = "file://docs/EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

INC_PR = "r2"

INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dev = "ldflags"

SRC_URI = "${FSL_MIRROR}/fsl-mm-codeclib-${PV}.bin;fsl-eula=true"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/mm_ghdr

    cp -r ${S}/release/lib/* ${D}${libdir}
    cp -r ${S}/ghdr/* ${D}${includedir}/mm_ghdr
    install -m 0644 ${S}/pkgconfig/fsl-mm-core.pc ${D}${libdir}/pkgconfig

	# Fix file permissions
	find ${D} -type f -exec chmod a-x '{}' ';'
}

FILES_${PN} += "${libdir}/${SOLIBS}"
FILES_${PN}-dev += "${libdir}/${SOLIBSDEV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
