# Copyright 2020-2023 NXP

SUMMARY = "Wi-Fi firmware redistributed by NXP"
DESCRIPTION = "Additional Wi-Fi firmware redistributed by NXP. Some \
is available in linux-firmware, but what is here is the latest and \
should be preferred."

SECTION = "kernel"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=673fa34349fa40f59e0713cb0ac22b1f"

SRC_URI = "git://github.com/NXP/imx-firmware.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.1.22_2.0.0"
SRCREV = "f775d53ca3a478c85e8c8a880e44cc269bd14db0"

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
    install -m 0644 nxp/FwImage_8987/sd8987_wlan.bin           ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8987/sdiouart8987_combo_v0.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8987/txpwrlimit_cfg_8987.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8987/uartuart8987_bt.bin       ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity PCIE8997 firmware
    install -m 0644 nxp/FwImage_8997/ed_mac_ctrl_V3_8997.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997/pcie8997_wlan_v4.bin      ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997/pcieuart8997_combo_v4.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997/txpwrlimit_cfg_8997.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997/uartuart8997_bt_v4.bin    ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity SDIO8997 firmware
    install -m 0644 nxp/FwImage_8997_SD/ed_mac_ctrl_V3_8997.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997_SD/sdio8997_wlan_v4.bin      ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997_SD/sdiouart8997_combo_v4.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_8997_SD/txpwrlimit_cfg_8997.conf  ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity PCIE9098 firmware
    install -m 0644 nxp/FwImage_9098_PCIE/ed_mac_ctrl_V3_909x.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_9098_PCIE/pcie9098_wlan_v1.bin      ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_9098_PCIE/pcieuart9098_combo_v1.bin ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_9098_PCIE/txpwrlimit_cfg_9098.conf  ${D}${nonarch_base_libdir}/firmware/nxp
    install -m 0644 nxp/FwImage_9098_PCIE/uartuart9098_bt_v1.bin    ${D}${nonarch_base_libdir}/firmware/nxp

    # Install NXP Connectivity SDIO9098 firmware
    install -m 0644 nxp/FwImage_9098_SD/sdio9098_wlan_v1.bin      ${D}${nonarch_base_libdir}/firmware/nxp
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
    ${PN}-nxp9098-common \
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
RPROVIDES:${PN}-nxp8987-sdio = "linux-firmware-nxp8987-sdio"
RREPLACES:${PN}-nxp8987-sdio = "linux-firmware-nxp8987-sdio"
RCONFLICTS:${PN}-nxp8987-sdio = "linux-firmware-nxp8987-sdio"

FILES:${PN}-nxp8997-common = " \
    ${nonarch_base_libdir}/firmware/nxp/ed_mac_ctrl_V3_8997.conf \
    ${nonarch_base_libdir}/firmware/nxp/txpwrlimit_cfg_8997.conf \
    ${nonarch_base_libdir}/firmware/nxp/uartuart8997_bt_v4.bin \
"
RDEPENDS:${PN}-nxp8997-common += "${PN}-nxp-common"
RPROVIDES:${PN}-nxp8997-common = "linux-firmware-nxp8997-common"
RREPLACES:${PN}-nxp8997-common = "linux-firmware-nxp8997-common"
RCONFLICTS:${PN}-nxp8997-common = "linux-firmware-nxp8997-common"

FILES:${PN}-nxp8997-pcie = " \
    ${nonarch_base_libdir}/firmware/nxp/pci*8997* \
"
RDEPENDS:${PN}-nxp8997-pcie += "${PN}-nxp8997-common"
RPROVIDES:${PN}-nxp8997-pcie = "linux-firmware-nxp8997-pcie"
RREPLACES:${PN}-nxp8997-pcie = "linux-firmware-nxp8997-pcie"
RCONFLICTS:${PN}-nxp8997-pcie = "linux-firmware-nxp8997-pcie"

FILES:${PN}-nxp8997-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/sdio*8997* \
"
RDEPENDS:${PN}-nxp8997-sdio += "${PN}-nxp8997-common"
RPROVIDES:${PN}-nxp8997-sdio = "linux-firmware-nxp8997-sdio"
RREPLACES:${PN}-nxp8997-sdio = "linux-firmware-nxp8997-sdio"
RCONFLICTS:${PN}-nxp8997-sdio = "linux-firmware-nxp8997-sdio"

FILES:${PN}-nxp9098-common = " \
    ${nonarch_base_libdir}/firmware/nxp/ed_mac_ctrl_V3_909x.conf \
    ${nonarch_base_libdir}/firmware/nxp/txpwrlimit_cfg_9098.conf \
    ${nonarch_base_libdir}/firmware/nxp/uartuart9098_bt_v1.bin \
"
RDEPENDS:${PN}-nxp9098-common += "${PN}-nxp-common"
RPROVIDES:${PN}-nxp9098-common = "linux-firmware-nxp9098-common"
RREPLACES:${PN}-nxp9098-common = "linux-firmware-nxp9098-common"
RCONFLICTS:${PN}-nxp9098-common = "linux-firmware-nxp9098-common"

FILES:${PN}-nxp9098-pcie = " \
    ${nonarch_base_libdir}/firmware/nxp/pcie*9098* \
"
RDEPENDS:${PN}-nxp9098-pcie += "${PN}-nxp9098-common"
RPROVIDES:${PN}-nxp9098-pcie = "linux-firmware-nxp9098-pcie"
RREPLACES:${PN}-nxp9098-pcie = "linux-firmware-nxp9098-pcie"
RCONFLICTS:${PN}-nxp9098-pcie = "linux-firmware-nxp9098-pcie"

FILES:${PN}-nxp9098-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/sdio*9098* \
"
RDEPENDS:${PN}-nxp9098-sdio += "${PN}-nxp9098-common"
RPROVIDES:${PN}-nxp9098-sdio = "linux-firmware-nxp9098-sdio"
RREPLACES:${PN}-nxp9098-sdio = "linux-firmware-nxp9098-sdio"
RCONFLICTS:${PN}-nxp9098-sdio = "linux-firmware-nxp9098-sdio"

FILES:${PN}-nxpiw416-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/*iw416* \
"
RDEPENDS:${PN}-nxpiw416-sdio += "${PN}-nxp-common"
RPROVIDES:${PN}-nxpiw416-sdio = "linux-firmware-nxpiw416-sdio"
RREPLACES:${PN}-nxpiw416-sdio = "linux-firmware-nxpiw416-sdio"
RCONFLICTS:${PN}-nxpiw416-sdio = "linux-firmware-nxpiw416-sdio"

FILES:${PN}-nxpiw612-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/sduart_nw61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/sd_w61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/uartspi_n61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/IW612_SD_RFTest/ \
"
RDEPENDS:${PN}-nxpiw612-sdio += "${PN}-nxp-common"
RPROVIDES:${PN}-nxpiw612-sdio = "linux-firmware-nxpiw612-sdio"
RREPLACES:${PN}-nxpiw612-sdio = "linux-firmware-nxpiw612-sdio"
RCONFLICTS:${PN}-nxpiw612-sdio = "linux-firmware-nxpiw612-sdio"
