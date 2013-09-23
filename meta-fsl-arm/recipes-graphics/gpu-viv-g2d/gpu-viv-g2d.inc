# Copyright (C) 2013 Freescale Semiconductor

DESCRIPTION = "GPU Vivante G2D library and sample applications for imx6"
SECTION = "libs"
LICENSE = "Proprietary"
PROVIDES += "virtual/libg2d"

inherit fsl-eula-unpack

PE = "1"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

PACKAGES = "gpu-viv-g2d gpu-viv-g2d-dev gpu-viv-g2d-dbg"

# Inhibit warnings about files being stripped.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

S = "${WORKDIR}/${PN}-${PV}"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}

    cp ${S}/usr/lib/*.so ${D}${libdir}
    cp -axr ${S}/usr/include/* ${D}${includedir}
    cp -axr ${S}/opt ${D}

    find ${D}${libdir} -type f -exec chmod 644 {} \;
    find ${D}${includedir} -type f -exec chmod 644 {} \;
}

# FIXME: The provided binary doesn't provide soname. If in future BSP
# release the libraries are fixed, we can drop this hack.
REALSOLIBS := "${SOLIBS}"
SOLIBS = "${SOLIBSDEV}"

FILES_${PN} = "/opt ${libdir}/libg2d${SOLIBS} "
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dbg = "${libdir}/.debug/libg2d${SOLIBS}"

COMPATIBLE_MACHINE = "(mx6)"
