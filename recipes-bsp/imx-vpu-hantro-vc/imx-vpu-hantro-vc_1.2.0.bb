# Copyright (C) 2019-2020 NXP

DESCRIPTION = "i.MX VC8000E Encoder library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=983e4c77621568488dd902b27e0c2143"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

S = "${WORKDIR}/${BPN}-${PV}"

SRC_URI[md5sum] = "d2b7c0cfdb380e5a65a94251c2437a34"
SRC_URI[sha256sum] = "10a7f60964feb9641291815876fb8110dfec603c6451ed9b9c7fe57c23afd10e"

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

FILES_${PN} = "/"

COMPATIBLE_MACHINE = "(mx8mp)"
