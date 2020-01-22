# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SUMMARY = "Freescale i.MX firmware"
DESCRIPTION = "Freescale i.MX firmware such as for the VPU"

require firmware-imx-${PV}.inc

PE = "1"

SRC_URI += " \
    git://github.com/NXP/imx-firmware.git;protocol=https;branch=${SRCBRANCH};destsuffix=${S}/git \
"
SRCREV = "8ce9046f5058fdd2c5271f86ccfc61bc5a248ae3"

inherit allarch

do_install() {
    install -d ${D}${base_libdir}/firmware/imx

    cd firmware
    for d in *; do
        case $d in
        ddr|hdmi|seco)
            # These folders are for i.MX 8 and are included in the boot image via imx-boot
            bbnote Excluding folder $d
            ;;
        *)
            cp -rfv $d ${D}${base_libdir}/firmware
            ;;
        esac
    done
    cd -

    mv ${D}${base_libdir}/firmware/epdc/ ${D}${base_libdir}/firmware/imx/epdc/
    mv ${D}${base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw.nonrestricted ${D}${base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw

    find ${D}${base_libdir}/firmware -type f -exec chmod 644 '{}' ';'
    find ${D}${base_libdir}/firmware -type f -exec chown root:root '{}' ';'

    # Remove files not going to be installed
    rm ${D}${base_libdir}/firmware/sdma/sdma-imx6q.bin
    rm ${D}${base_libdir}/firmware/sdma/sdma-imx7d.bin
    find ${D}${base_libdir}/firmware/ -name '*.mk' -exec rm '{}' ';'
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

PACKAGES =+ "${PN}-epdc ${PN}-scfw ${PN}-sdma"

FILES_${PN}-epdc = "${base_libdir}/firmware/imx/epdc/"
FILES_${PN}-scfw = "${base_libdir}/firmware/scfw/"
FILES_${PN}-sdma = " ${base_libdir}/firmware/imx/sdma"

COMPATIBLE_MACHINE = "(imx)"
