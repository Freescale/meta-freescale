FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:imx-nxp-bsp = " file://51-bluez-imx.conf \
                               file://80-disable-logind.conf \
                               file://0001-wpctl-fix-set-default-Segmentation-fault-on-32bit-pl.patch \
                             "

do_install:append:imx-nxp-bsp () {

    install -d ${D}${datadir}

    # Install nxp configure file to /usr/share/wireplumber/wireplumber.conf.d
    install -d ${D}${datadir}/wireplumber/wireplumber.conf.d
    install -m 0644 ${UNPACKDIR}/51-bluez-imx.conf ${D}${datadir}/wireplumber/wireplumber.conf.d
    install -m 0644 ${UNPACKDIR}/80-disable-logind.conf ${D}${datadir}/wireplumber/wireplumber.conf.d
}

FILES:${PN}:append = " ${datadir}/wireplumber/wireplumber.conf.d"
