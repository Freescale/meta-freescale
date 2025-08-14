require mali-imx.inc

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "83a5be75384a92f0305516785ffbefe72683eba5a4ebf03ec7c1b7d7cdc76064"
IMX_SRCREV_ABBREV = "696f9a6"

S = "${WORKDIR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}
    cp -r ${S}/etc ${S}/usr ${D}

    # Move firmware to nonarch_base_libdir
    if [ "${base_libdir}" != "${nonarch_base_libdir}" ]; then
        install -d ${D}${nonarch_base_libdir}
        mv ${D}${base_libdir}/firmware ${D}${nonarch_base_libdir}
    fi
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
