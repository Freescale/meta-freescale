# Copyright (C) 2019-2021 NXP

DESCRIPTION = "i.MX VC8000E Encoder library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=e565271ec9a80ce47abbddc4bffe56fa"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

S = "${WORKDIR}/${BPN}-${PV}"

SRC_URI[md5sum] = "fbd43a100c27abbae301428f1ae2bc64"
SRC_URI[sha256sum] = "e59ac2ded726d722f61793b17fc394ce87b1c5710cb79f0b1d60b87fca5829e1"

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

COMPATIBLE_MACHINE = "(mx8mp)"
