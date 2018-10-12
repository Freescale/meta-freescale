# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SUMMARY = "Freescale i.MX firmware"
DESCRIPTION = "Freescale i.MX firmware such as for the VPU"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ab1a30d0cd181e3408077727ea5a2db"

PE = "1"

SRCBRANCH ?= "master"
SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
           git://github.com/NXP/imx-firmware.git;protocol=https;branch=${SRCBRANCH};destsuffix=${S}/git"

#BRCM firmware git
SRCREV = "8ce9046f5058fdd2c5271f86ccfc61bc5a248ae3"

SRC_URI[md5sum] = "0967aa59b3fd8d80fcb98146a9aac91b"
SRC_URI[sha256sum] = "910fbf866f61185adfd60c1704b2da41030cb175901d06e40402b49f9240bdee"

inherit fsl-eula-unpack allarch deploy

do_install() {
    install -d ${D}${base_libdir}/firmware/imx
    install -d ${D}${base_libdir}/firmware/bcm
    install -d ${D}${sysconfdir}/firmware

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

    #1BW_BCM43340
    install -d ${D}${base_libdir}/firmware/bcm/1BW_BCM43340
    cp -rfv git/brcm/1BW_BCM43340/*.bin ${D}${base_libdir}/firmware/bcm/1BW_BCM43340
    cp -rfv git/brcm/1BW_BCM43340/*.cal ${D}${base_libdir}/firmware/bcm/1BW_BCM43340
    cp -rfv git/brcm/1BW_BCM43340/*.hcd ${D}${sysconfdir}/firmware/

    #1CX_BCM4356
    install -d ${D}${base_libdir}/firmware/bcm/1CX_BCM4356
    cp -rfv git/brcm/1CX_BCM4356/fw_bcmdhd.bin ${D}${base_libdir}/firmware/bcm/1CX_BCM4356

    #1DX_BCM4343W
    install -d ${D}${base_libdir}/firmware/bcm/1DX_BCM4343W
    cp -rfv git/brcm/1DX_BCM4343W/*.bin ${D}${base_libdir}/firmware/bcm/1DX_BCM4343W
    cp -rfv git/brcm/1DX_BCM4343W/*.cal ${D}${base_libdir}/firmware/bcm/1DX_BCM4343W
    cp -rfv git/brcm/1DX_BCM4343W/*.hcd ${D}${sysconfdir}/firmware/

    #1FD_BCM89359
    install -d ${D}${base_libdir}/firmware/bcm/1FD_BCM89359
    cp -rfv git/brcm/1FD_BCM89359/*.bin ${D}${base_libdir}/firmware/bcm/1FD_BCM89359
    cp -rfv git/brcm/1FD_BCM89359/*.hcd ${D}${sysconfdir}/firmware/

    #SN8000_BCM43362
    install -d ${D}${base_libdir}/firmware/bcm/SN8000_BCM43362
    cp -rfv git/brcm/SN8000_BCM43362/*.bin ${D}${base_libdir}/firmware/bcm/SN8000_BCM43362
    cp -rfv git/brcm/SN8000_BCM43362/*.cal ${D}${base_libdir}/firmware/bcm/SN8000_BCM43362
    cp -rfv git/brcm/1DX_BCM4343W/*.hcd ${D}${sysconfdir}/firmware/

    #ZP_BCM4339
    install -d ${D}${base_libdir}/firmware/bcm/ZP_BCM4339
    cp -rfv git/brcm/ZP_BCM4339/*.bin ${D}${base_libdir}/firmware/bcm/ZP_BCM4339
    cp -rfv git/brcm/ZP_BCM4339/*.cal ${D}${base_libdir}/firmware/bcm/ZP_BCM4339
    cp -rfv git/brcm/ZP_BCM4339/*.hcd ${D}${sysconfdir}/firmware/

    mv ${D}${base_libdir}/firmware/epdc/ ${D}${base_libdir}/firmware/imx/epdc/
    mv ${D}${base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw.nonrestricted ${D}${base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw

    find ${D}${base_libdir}/firmware -type f -exec chmod 644 '{}' ';'
    find ${D}${base_libdir}/firmware -type f -exec chown root:root '{}' ';'

    # Remove files not going to be installed
    find ${D}${base_libdir}/firmware/ -name '*.mk' -exec rm '{}' ';'
}

do_deploy() {
}
do_deploy_append_mx8m() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}
    done
    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_dp_imx8m.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_hdmi_imx8m.bin ${DEPLOYDIR}
}
do_deploy_append_mx8qm() {
    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmirxfw.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${DEPLOYDIR}
    # SECO
    install -m 0644 ${S}/firmware/seco/mx8qm-ahab-container.img ${DEPLOYDIR}
}
do_deploy_append_mx8x() {
    # SECO
    install -m 0644 ${S}/firmware/seco/mx8qx-ahab-container.img ${DEPLOYDIR}
}
addtask deploy before do_build after do_install

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

PACKAGES =+ "${PN}-epdc ${PN}-brcm ${PN}-scfw"

FILES_${PN}-epdc = "${base_libdir}/firmware/imx/epdc/"
FILES_${PN}-brcm = "${base_libdir}/firmware/bcm/*/*.bin ${base_libdir}/firmware/bcm/*/*.cal ${sysconfdir}/firmware/"
FILES_${PN}-scfw = "${base_libdir}/firmware/scfw/"

COMPATIBLE_MACHINE = "(imx)"
