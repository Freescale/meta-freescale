# Copyright 2019-2022 NXP

DESCRIPTION = "i.MX VC8000E Encoder library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

inherit fsl-eula-unpack

SRC_URI = " ${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
IMX_SRCREV_ABBREV = "5e18a84"

SRC_URI[sha256sum] = "04d2a9c5c75804a11a4500a0133dfc7d8e84f74254cee52c42075f5ecc229550"
S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

# SCR is the location and name of the Software Content Register file
# relative to ${D}${D_SUBDIR}.
SCR = "SCR.txt"

do_install () {
    install -d ${D}${D_SUBDIR}
    cp -r ${S}/* ${D}${D_SUBDIR}
    if [ -d "${D}/usr/lib" ] && [ "${D}/usr/lib" != "${D}${libdir}" ]; then
        mv ${D}/usr/lib ${D}${libdir}
    fi
    rm ${D}${D_SUBDIR}/COPYING
    if [ ! -f ${D}${D_SUBDIR}/${SCR} ]; then
        bbfatal "Missing Software Content Register \"${D}${D_SUBDIR}/${SCR}\""
    fi
    rm ${D}${D_SUBDIR}/${SCR}
}

FILES:${PN} = "/"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
