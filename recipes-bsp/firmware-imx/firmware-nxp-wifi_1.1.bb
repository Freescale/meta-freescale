# Copyright 2020-2026 NXP

SUMMARY = "Wi-Fi firmware redistributed by NXP"
DESCRIPTION = "Additional Wi-Fi firmware redistributed by NXP. Some \
is available in linux-firmware, but what is here is the latest and \
should be preferred."

SECTION = "kernel"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=bc649096ad3928ec06a8713b8d787eac"

SRC_URI = "git://github.com/nxp-imx/imx-firmware.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.12.49_2.2.0"
SRCREV = "8c9b278016c97527b285f2fcbe53c2d428eb171d"


inherit allarch

CLEANBROKEN = "1"
ALLOW_EMPTY:${PN} = "1"
ALLOW_EMPTY:${PN}-all-pcie = "1"
ALLOW_EMPTY:${PN}-all-sdio = "1"
ALLOW_EMPTY:${PN}-all-usb = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/nxp
    oe_runmake install INSTALLDIR=${D}${nonarch_base_libdir}/firmware/nxp

    # Upstream SDIO8997 and IW416 driver firmwares are located on mrvl folder
    install -d ${D}${nonarch_base_libdir}/firmware/mrvl
    ln -frs ${D}${nonarch_base_libdir}/firmware/nxp/sdiouart8997_combo_v4.bin ${D}${nonarch_base_libdir}/firmware/mrvl/sdiouart8997_combo_v4.bin
    ln -frs ${D}${nonarch_base_libdir}/firmware/nxp/sdiouartiw416_combo_v0.bin ${D}${nonarch_base_libdir}/firmware/mrvl/sdiouartiw416_combo_v0.bin

    ln -frs ${D}${nonarch_base_libdir}/firmware/nxp/sd9098_wlan_v1.bin ${D}${nonarch_base_libdir}/firmware/nxp/sdio9098_wlan_v1.bin
    ln -frs ${D}${nonarch_base_libdir}/firmware/nxp/sduart9098_combo_v1.bin ${D}${nonarch_base_libdir}/firmware/nxp/sdiouart9098_combo_v1.bin
    ln -frs ${D}${nonarch_base_libdir}/firmware/nxp/sd8997_wlan_v4.bin ${D}${nonarch_base_libdir}/firmware/nxp/sdio8997_wlan_v4.bin
    ln -frs ${D}${nonarch_base_libdir}/firmware/nxp/sduart8997_combo_v4.bin ${D}${nonarch_base_libdir}/firmware/nxp/sdiouart8997_combo_v4.bin
    ln -frs ${D}${nonarch_base_libdir}/firmware/nxp/sduartiw416_combo.bin ${D}${nonarch_base_libdir}/firmware/nxp/sdiouartiw416_combo_v0.bin
}

PACKAGES =+ " \
    ${PN}-all-pcie \
    ${PN}-all-sdio \
    ${PN}-all-usb \
    \
    ${PN}-nxp-common \
    ${PN}-nxp8987-sdio \
    ${PN}-nxp8997-common \
    ${PN}-nxp8997-pcie \
    ${PN}-nxp8997-sdio \
    ${PN}-nxp9098-common \
    ${PN}-nxp9098-pcie \
    ${PN}-nxp9098-sdio \
    ${PN}-nxpaw693-pcie \
    ${PN}-nxpiw416-sdio \
    ${PN}-nxpiw610-sdio \
    ${PN}-nxpiw610-usb \
    ${PN}-nxpiw612-sdio \
"

RDEPENDS:${PN}-all-sdio = " \
    ${PN}-nxp8987-sdio \
    ${PN}-nxp8997-sdio \
    ${PN}-nxp9098-sdio \
    ${PN}-nxpiw416-sdio \
    ${PN}-nxpiw610-sdio \
    ${PN}-nxpiw612-sdio \
"

RDEPENDS:${PN}-all-pcie = " \
    ${PN}-nxp8997-pcie \
    ${PN}-nxp9098-pcie \
    ${PN}-nxpaw693-pcie \
"

RDEPENDS:${PN}-all-usb = " \
    ${PN}-nxpiw610-usb \
"

FILES:${PN}-nxp-common = " \
    ${nonarch_base_libdir}/firmware/nxp/wifi_mod_para.conf \
    ${nonarch_base_libdir}/firmware/nxp/helper_uart_3000000.bin \
"

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
    ${nonarch_base_libdir}/firmware/nxp/uart8997_bt_v4.bin \
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
    ${nonarch_base_libdir}/firmware/mrvl/sdiouart8997_combo_v4.bin \
    ${nonarch_base_libdir}/firmware/nxp/sd*8997* \
"
RDEPENDS:${PN}-nxp8997-sdio += "${PN}-nxp8997-common"
RPROVIDES:${PN}-nxp8997-sdio = "linux-firmware-nxp8997-sdio"
RREPLACES:${PN}-nxp8997-sdio = "linux-firmware-nxp8997-sdio"
RCONFLICTS:${PN}-nxp8997-sdio = "linux-firmware-nxp8997-sdio"

FILES:${PN}-nxp9098-common = " \
    ${nonarch_base_libdir}/firmware/nxp/ed_mac_ctrl_V3_909x.conf \
    ${nonarch_base_libdir}/firmware/nxp/txpwrlimit_cfg_9098.conf \
    ${nonarch_base_libdir}/firmware/nxp/uart9098_bt_v1.bin \
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
    ${nonarch_base_libdir}/firmware/nxp/sd*9098* \
"
RDEPENDS:${PN}-nxp9098-sdio += "${PN}-nxp9098-common"
RPROVIDES:${PN}-nxp9098-sdio = "linux-firmware-nxp9098-sdio"
RREPLACES:${PN}-nxp9098-sdio = "linux-firmware-nxp9098-sdio"
RCONFLICTS:${PN}-nxp9098-sdio = "linux-firmware-nxp9098-sdio"

FILES:${PN}-nxpaw693-pcie += " \
    ${nonarch_base_libdir}/firmware/nxp/pcie*aw693* \
    ${nonarch_base_libdir}/firmware/nxp/uart*aw693* \
"
RDEPENDS:${PN}-nxpaw693-pcie += "${PN}-nxp-common"

FILES:${PN}-nxpiw416-sdio = " \
    ${nonarch_base_libdir}/firmware/mrvl/sdiouartiw416_combo_v0.bin \
    ${nonarch_base_libdir}/firmware/nxp/*iw416* \
"
RDEPENDS:${PN}-nxpiw416-sdio += "${PN}-nxp-common"
RPROVIDES:${PN}-nxpiw416-sdio = "linux-firmware-nxpiw416-sdio"
RREPLACES:${PN}-nxpiw416-sdio = "linux-firmware-nxpiw416-sdio"
RCONFLICTS:${PN}-nxpiw416-sdio = "linux-firmware-nxpiw416-sdio"

FILES:${PN}-nxpiw610-sdio += " \
    ${nonarch_base_libdir}/firmware/nxp/sd_iw610.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/sduart_iw610.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/sduartspi_iw610.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/uart_iw610_bt.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/uartspi_iw610.bin.se \
"
RDEPENDS:${PN}-nxpiw610-sdio += "${PN}-nxp-common"
RPROVIDES:${PN}-nxpiw610-sdio = "linux-firmware-nxpiw610-sdio"
RREPLACES:${PN}-nxpiw610-sdio = "linux-firmware-nxpiw610-sdio"
RCONFLICTS:${PN}-nxpiw610-sdio = "linux-firmware-nxpiw610-sdio"

FILES:${PN}-nxpiw610-usb += " \
    ${nonarch_base_libdir}/firmware/nxp/usb*_iw610.bin.se \
"
RDEPENDS:${PN}-nxpiw610-usb += "${PN}-nxp-common"

FILES:${PN}-nxpiw612-sdio = " \
    ${nonarch_base_libdir}/firmware/nxp/sduart_nw61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/sd_w61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/uartspi_n61x_v1.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/IW612_SD_RFTest/ \
    ${nonarch_base_libdir}/firmware/nxp/uartuart_n61x_v1.bin.se \
"
RDEPENDS:${PN}-nxpiw612-sdio += "${PN}-nxp-common"
RPROVIDES:${PN}-nxpiw612-sdio = "linux-firmware-nxpiw612-sdio"
RREPLACES:${PN}-nxpiw612-sdio = "linux-firmware-nxpiw612-sdio"
RCONFLICTS:${PN}-nxpiw612-sdio = "linux-firmware-nxpiw612-sdio"
