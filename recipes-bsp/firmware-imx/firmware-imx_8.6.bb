# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SUMMARY = "Freescale i.MX firmware"
DESCRIPTION = "Freescale i.MX firmware such as for the VPU"

require firmware-imx-${PV}.inc

SRC_URI_append = " \
    file://sdma \
    file://epdc \
    file://regulatory \
    file://hdmi \
"

PE = "1"

inherit allarch

do_install() {
    install -d ${D}${base_libdir}/firmware/imx

    # Install loading scripts
    install -d ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/sdma ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/epdc ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/regulatory ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/hdmi ${D}${sysconfdir}

    cd firmware
    for d in *; do
        case $d in
        ddr|seco)
            # These folders are for i.MX 8 and are included in the boot image via imx-boot
            bbnote Excluding folder $d
            ;;
        *)
            cp -rfv $d ${D}${base_libdir}/firmware
            ;;
        esac
    done
    cd -

    # Install SDMA Firmware: sdma-imx6q.bin & sdma-imx7d.bin into lib/firmware/imx/sdma
    install -d ${D}${base_libdir}/firmware/imx/sdma
    mv ${D}${base_libdir}/firmware/sdma/sdma-imx6q.bin ${D}${base_libdir}/firmware/imx/sdma
    mv ${D}${base_libdir}/firmware/sdma/sdma-imx7d.bin ${D}${base_libdir}/firmware/imx/sdma

    mv ${D}${base_libdir}/firmware/epdc/ ${D}${base_libdir}/firmware/imx/epdc/
    mv ${D}${base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw.nonrestricted ${D}${base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw

    mv ${D}${base_libdir}/firmware/easrc/ ${D}${base_libdir}/firmware/imx/easrc/

    # Install HDMI Firmware: hdmitxfw.bin, hdmirxfw.bin & dpfw.bin into lib/firmware/imx/hdmi
    install -d ${D}${base_libdir}/firmware/imx/hdmi
    mv ${D}${base_libdir}/firmware/hdmi/cadence/hdmitxfw.bin ${D}${base_libdir}/firmware/imx/hdmi
    mv ${D}${base_libdir}/firmware/hdmi/cadence/hdmirxfw.bin ${D}${base_libdir}/firmware/imx/hdmi
    mv ${D}${base_libdir}/firmware/hdmi/cadence/dpfw.bin ${D}${base_libdir}/firmware/imx/hdmi

    find ${D}${base_libdir}/firmware -type f -exec chmod 644 '{}' ';'
    find ${D}${base_libdir}/firmware -type f -exec chown root:root '{}' ';'

    # Remove files not going to be installed
    find ${D}${base_libdir}/firmware/ -name '*.mk' -exec rm '{}' ';'
    rm -rf ${D}${base_libdir}/firmware/hdmi
}

python populate_packages_prepend() {
    vpudir = bb.data.expand('${base_libdir}/firmware/vpu', d)
    do_split_packages(d, vpudir, '^vpu_fw_([^_]*).*\.bin',
                      output_pattern='firmware-imx-vpu-%s',
                      description='Freescale IMX Firmware %s',
                      extra_depends='',
                      prepend=True)

    sdmadir = bb.data.expand('${base_libdir}/firmware/sdma', d)
    do_split_packages(d, sdmadir, '^sdma-([^-]*).*\.bin',
                      output_pattern='firmware-imx-sdma-%s',
                      description='Freescale IMX Firmware %s',
                      extra_depends='',
                      prepend=True)
}

ALLOW_EMPTY_${PN} = "1"

PACKAGES_DYNAMIC = "${PN}-vpu-* ${PN}-sdma-*"

PACKAGES =+ "${PN}-epdc ${PN}-sdma ${PN}-easrc ${PN}-regulatory ${PN}-hdmi"

FILES_${PN}-epdc = "${base_libdir}/firmware/imx/epdc/ ${sysconfdir}/epdc"
FILES_${PN}-sdma = "${base_libdir}/firmware/imx/sdma ${sysconfdir}/sdma"
FILES_${PN}-easrc = "${base_libdir}/firmware/imx/easrc/"
FILES_${PN}-regulatory = "${sysconfdir}/regulatory"
FILES_${PN}-hdmi = "${base_libdir}/firmware/imx/hdmi/ ${sysconfdir}/hdmi"

COMPATIBLE_MACHINE = "(imx|use-mainline-bsp)"
