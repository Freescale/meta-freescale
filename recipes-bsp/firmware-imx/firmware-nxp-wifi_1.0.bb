# Copyright 2020-2023 NXP

SUMMARY = "Wi-Fi firmware redistributed by NXP"
DESCRIPTION = "Additional Wi-Fi firmware redistributed by NXP. Some \
is available in linux-firmware, but what is here is the latest and \
should be preferred."

SECTION = "kernel"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=44a8052c384584ba09077e85a3d1654f"

SRC_URI = "git://github.com/nxp-imx/imx-firmware.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.6.3_1.0.0"
SRCREV = "2afa15e77f0b58eade42b4f59c9215339efcca66"

S = "${WORKDIR}/git"

inherit allarch

CLEANBROKEN = "1"
ALLOW_EMPTY:${PN} = "1"

do_compile() {
	:
}

do_install() {

    install -d ${D}${nonarch_base_libdir}/firmware/nxp
    install -d ${D}${nonarch_base_libdir}/firmware/brcm/

    # Install bcm4359-pcie
    for f in cyw-wifi-bt/*_CYW*/brcmfmac4359-pcie*; do
        install -D -m 0644 $f ${D}${nonarch_base_libdir}/firmware/brcm/$(basename $f)
    done

    for f in cyw-wifi-bt/*_CYW*/BCM4349B1*.hcd; do
        install -D -m 0644 $f ${D}${sysconfdir}/firmware/$(basename $f)
    done

    for f in nxp/FwImage_IW612_SD/*; do
        install -D -m 0644 $f ${D}${nonarch_base_libdir}/firmware/nxp/IW612_SD_RFTest/$(basename $f)
    done

    oe_runmake install INSTALLDIR=${D}${nonarch_base_libdir}/firmware/nxp


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
    ${nonarch_base_libdir}/firmware/nxp/helper_uart_3000000.bin \
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

COMPATIBLE_MACHINE = "(imx-generic-bsp)"
