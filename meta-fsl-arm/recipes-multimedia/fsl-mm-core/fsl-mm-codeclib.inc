# Copyright (C) 2011-2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia codec libs"
LICENSE = "Proprietary"
SECTION = "multimedia"

INC_PR = "r4"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

inherit fsl-eula-unpack pkgconfig

# FIXME: Quiet down the warnings during package
INSANE_SKIP_${PN} = "ldflags textrel"
INSANE_SKIP_${PN}-dev = "ldflags textrel"

do_install () {
    install -d ${D}${includedir} ${D}${libdir}
    cp -ar ${S}/release/lib/* ${D}${libdir}
    rm -rf ${D}${includedir}/mm_ghdr
    cp -r ${S}/ghdr* ${D}${includedir}
    mv ${D}${includedir}/ghdr ${D}${includedir}/mm_ghdr

    # FIXME: Fix permissions
    find ${D} -type f -exec chmod 0644 '{}' ';'
}

FILES_${PN} += "${libdir}/lib*_elinux.3.0.1.07.00"

PACKAGE_ARCH = "${MACHINE_ARCH}"
