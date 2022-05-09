# Copyright (C) 2019-2021 NXP

DESCRIPTION = "i.MX VC8000E Encoder library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=be5ff43682ed6c57dfcbeb97651c2829"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

S = "${WORKDIR}/${BPN}-${PV}"

SRC_URI[md5sum] = "2dbcd9a71d53b86654704932798f263e"
SRC_URI[sha256sum] = "8af665f8e06e0e467f563e372f43583d94128f0d37f75321965bc26bd4404b2b"

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
