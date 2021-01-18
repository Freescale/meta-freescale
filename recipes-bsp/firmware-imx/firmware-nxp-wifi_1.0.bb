# Copyright 2020-2021 NXP

SUMMARY = "Wi-Fi firmware redistributed by NXP"
SECTION = "kernel"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://cyw-wifi-bt/EULA.txt;md5=80c0478f4339af024519b3723023fe28"

SRC_URI = "git://github.com/NXP/imx-firmware.git;protocol=https"
SRCREV = "484d38224fa2c26b8859a7bf20b7c4d49100f5bc"

S = "${WORKDIR}/git"

inherit allarch

CLEANBROKEN = "1"

do_compile() {
	:
}

do_install() {
    install -d ${D}${sysconfdir}/firmware
    install -d ${D}${nonarch_base_libdir}/firmware/brcm

    # Install various flavors of Broadcom firmware provided by Murata
    install -m 0644 cyw-wifi-bt/*_CYW*/brcmfmac* ${D}${nonarch_base_libdir}/firmware/brcm
    install -m 0644 cyw-wifi-bt/*_CYW*/BCM*.hcd ${D}${sysconfdir}/firmware
}

PACKAGES =+ " \
    ${PN}-bcm4339 \
    ${PN}-bcm43430 \
    ${PN}-bcm43455 \
    ${PN}-bcm4356-pcie \
    ${PN}-bcm4359-pcie \
"

FILES_${PN}-bcm4339 = " \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4339-sdio.* \
    ${sysconfdir}/firmware/BCM4335C0.ZP.hcd \
"

FILES_${PN}-bcm43430 = " \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.* \
    ${sysconfdir}/firmware/BCM43430A1.1DX.hcd \
"

FILES_${PN}-bcm43455 = " \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac43455-sdio.* \
    ${sysconfdir}/firmware/BCM4345C0.1MW.hcd \
"

FILES_${PN}-bcm4356-pcie = " \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4356-pcie.* \
    ${sysconfdir}/firmware/BCM4354A2.1CX.hcd \
"

FILES_${PN}-bcm4359-pcie = " \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4359-pcie.* \
    ${sysconfdir}/firmware/BCM4349B1_*.hcd \
"

RCONFLICTS_${PN}-bcm4339 = "linux-firmware-bcm4339"
RPROVIDES_${PN}-bcm4339 = "linux-firmware-bcm4339"
RREPLACES_${PN}-bcm4339 = "linux-firmware-bcm4339"

RCONFLICTS_${PN}-bcm43430 = "linux-firmware-bcm43430"
RPROVIDES_${PN}-bcm43430 = "linux-firmware-bcm43430"
RREPLACES_${PN}-bcm43430 = "linux-firmware-bcm43430"

RCONFLICTS_${PN}-bcm43455 = "linux-firmware-bcm43455"
RPROVIDES_${PN}-bcm43455 = "linux-firmware-bcm43455"
RREPLACES_${PN}-bcm43455 = "linux-firmware-bcm43455"

RCONFLICTS_${PN}-bcm4356-pcie = "linux-firmware-bcm4356-pcie"
RPROVIDES_${PN}-bcm4356-pcie = "linux-firmware-bcm4356-pcie"
RREPLACES_${PN}-bcm4356-pcie = "linux-firmware-bcm4356-pcie"

RCONFLICTS_${PN}-bcm4359-pcie = "linux-firmware-bcm4359-pcie"
RPROVIDES_${PN}-bcm4359-pcie = "linux-firmware-bcm4359-pcie"
RREPLACES_${PN}-bcm4359-pcie = "linux-firmware-bcm4359-pcie"
