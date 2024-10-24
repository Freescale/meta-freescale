require mali-imx.inc

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ca53281cc0caa7e320d4945a896fb837"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "a787a3285c3e288eedacf1a746de2bc9f5fdc15f35cf8b6147deb436183baf6f"
IMX_SRCREV_ABBREV = "39604c3"

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
