# Copyright 2020-2021 NXP

SUMMARY = "Wi-Fi firmware redistributed by NXP"
DESCRIPTION = "Additional Wi-Fi firmware redistributed by NXP, \
which is not covered by linux-firmware package. Once package becomes \
available as a part of linux-firmware - it can be dropped from this \
recipe in favor of upstream."

SECTION = "kernel"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=add2d392714d3096ed7e0f7e2190724b"

SRC_URI = "git://github.com/NXP/imx-firmware.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.1.1_1.0.0"
SRCREV = "bacbeb4789c1b13d13aab12ada29217ce8c3e905"

S = "${WORKDIR}/git"

inherit allarch

CLEANBROKEN = "1"
ALLOW_EMPTY:${PN} = "1"

do_compile() {
	:
}

do_install() {
    # Install various flavors of Broadcom firmware provided by Murata:
    # - bcm4359-pcie
    for f in cyw-wifi-bt/*_CYW*/brcmfmac4359-pcie*; do
        install -D -m 0644 $f ${D}${nonarch_base_libdir}/firmware/brcm/$(basename $f)
    done

    for f in cyw-wifi-bt/*_CYW*/BCM4349B1*.hcd; do
        install -D -m 0644 $f ${D}${sysconfdir}/firmware/$(basename $f)
    done

    # Install NXP Connectivity common
    install -D -m 0644 nxp/wifi_mod_para.conf ${D}${nonarch_base_libdir}/firmware/nxp/wifi_mod_para.conf

    # Install NXP Connectivity SD8801 firmware
    install -m 0644 nxp/FwImage_8801_SD/ed_mac_ctrl_V1_8801.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8801_SD/sd8801_uapsta.bin         ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity SDIO8987 firmware
    install -m 0644 nxp/FwImage_8987/ed_mac_ctrl_V3_8987.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8987/sdiouart8987_combo_v0.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8987/txpwrlimit_cfg_8987.conf  ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity PCIE8997 firmware
    install -m 0644 nxp/FwImage_8997/ed_mac_ctrl_V3_8997.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997/pcieuart8997_combo_v4.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997/txpwrlimit_cfg_8997.conf  ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity SDIO8997 firmware
    install -m 0644 nxp/FwImage_8997_SD/ed_mac_ctrl_V3_8997.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997_SD/sdiouart8997_combo_v4.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997_SD/txpwrlimit_cfg_8997.conf  ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity PCIE9098 firmware
    install -m 0644 nxp/FwImage_9098_PCIE/ed_mac_ctrl_V3_909x.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_9098_PCIE/pcieuart9098_combo_v1.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_9098_PCIE/txpwrlimit_cfg_9098.conf  ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity SDIO9098 firmware
    install -m 0644 nxp/FwImage_9098_SD/sdiouart9098_combo_v1.bin ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity IW416 firmware
    install -m 0644 nxp/FwImage_IW416_SD/sdioiw416_wlan_v0.bin      ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_IW416_SD/sdiouartiw416_combo_v0.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_IW416_SD/uartiw416_bt_v0.bin        ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity IW612 firmware
    install -m 0644 nxp/FwImage_IW612_SD/sduart_nw61x_v1.bin.se ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_IW612_SD/sd_w61x_v1.bin.se      ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_IW612_SD/uartspi_n61x_v1.bin.se ${D}${nonarch_base_libdir}/firmware/nxp
    for f in nxp/FwImage_IW612_SD/IW612_SD_RFTest/*; do
        install -D -m 0644 $f ${D}${nonarch_base_libdir}/firmware/nxp/IW612_SD_RFTest/$(basename $f)
    done
}

PACKAGES =+ " \
    ${PN}-bcm4359-pcie \
    ${PN}-nxp-common \
    ${PN}-nxp8801-sdio \
    ${PN}-nxp8987-sdio \
    ${PN}-nxp8997-common \
    ${PN}-nxp8997-pcie \
    ${PN}-nxp8997-sdio \
    ${PN}-nxp9098-pcie \
    ${PN}-nxp9098-sdio \
    ${PN}-nxpiw416-sdio \
    ${PN}-nxpiw612-sdio \
"

FILES:${PN}-bcm4359-pcie = " \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4359-pcie.* \
    ${sysconfdir}/firmware/BCM4349B1_*.hcd \
"

FILES:${PN}-nxp-common = " \
    ${nonarch_base_libdir}/firmware/nxp/wifi_mod_para.conf \
"

FILES:${PN}-nxp8801-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/*8801* \
"
RDEPENDS:${PN}-nxp8801-sdio += "${PN}-nxp-common"

FILES:${PN}-nxp8987-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/*8987* \
"
RDEPENDS:${PN}-nxp8987-sdio += "${PN}-nxp-common"

FILES:${PN}-nxp8997-common = " \
    ${nonarch_base_libdir}/firmware/nxp/ed_mac_ctrl_V3_8997.conf \
    ${nonarch_base_libdir}/firmware/nxp/txpwrlimit_cfg_8997.conf \
"
RDEPENDS:${PN}-nxp8997-common += "${PN}-nxp-common"

FILES:${PN}-nxp8997-pcie = " \
    ${nonarch_base_libdir}/firmware/nxp/pcieuart8997* \
"
RDEPENDS:${PN}-nxp8997-pcie += "${PN}-nxp8997-common"

FILES:${PN}-nxp8997-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/sdiouart8997* \
"
RDEPENDS:${PN}-nxp8997-sdio += "${PN}-nxp8997-common"

FILES:${PN}-nxp9098-pcie = " \
    ${nonarch_base_libdir}/firmware/nxp/ed_mac_ctrl_V3_909x.conf \
    ${nonarch_base_libdir}/firmware/nxp/pcieuart9098* \
    ${nonarch_base_libdir}/firmware/nxp/txpwrlimit_cfg_9098.conf \
"
RDEPENDS:${PN}-nxp9098-pcie += "${PN}-nxp-common"

FILES:${PN}-nxp9098-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/sdiouart9098* \
"
RDEPENDS:${PN}-nxp9098-sdio += "${PN}-nxp-common"

FILES:${PN}-nxpiw416-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/*iw416* \
"
RDEPENDS:${PN}-nxpiw416-sdio += "${PN}-nxp-common"

FILES:${PN}-nxpiw612-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/sduart_nw61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/sd_w61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/uartspi_n61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/IW612_SD_RFTest/ \
"
RDEPENDS:${PN}-nxpiw612-sdio += "${PN}-nxp-common"
