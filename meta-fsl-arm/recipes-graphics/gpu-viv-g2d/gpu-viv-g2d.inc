# Copyright (C) 2013, 2014 Freescale Semiconductor

DESCRIPTION = "GPU Vivante G2D library and sample applications for imx6"
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://usr/include/g2d.h;endline=7;md5=861ebad4adc7236f8d1905338abd7eb2"

PROVIDES += "virtual/libg2d"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

inherit fsl-eula-unpack

PE = "1"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

PACKAGES += "libg2d"

# Inhibit warnings about files being stripped.
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}

    cp ${S}/usr/lib/*.so ${D}${libdir}
    cp -Pr ${S}/usr/include/* ${D}${includedir}
    cp -Pr ${S}/opt ${D}

    find ${D}${libdir} -type f -exec chmod 644 {} \;
    find ${D}${includedir} -type f -exec chmod 644 {} \;
}

# FIXME: The provided binary doesn't provide soname. If in future BSP
# release the libraries are fixed, we can drop this hack.
REALSOLIBS := "${SOLIBS}"
SOLIBS = "${SOLIBSDEV}"

FILES_${PN} = "/opt"
FILES_libg2d =  "${libdir}/libg2d-viv${SOLIBS} ${libdir}/libg2d${SOLIBS}"
FILES_${PN}-dev = "${includedir}"

COMPATIBLE_MACHINE = "(mx6)"
