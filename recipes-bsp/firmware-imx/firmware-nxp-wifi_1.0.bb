# Copyright 2020-2021 NXP

SUMMARY = "Wi-Fi firmware redistributed by NXP"
DESCRIPTION = "Additional Wi-Fi firmware redistributed by NXP, \
which is not covered by linux-firmware package. Once package becomes \
available as a part of linux-firmware - it can be dropped from this \
recipe in favor of upstream."

SECTION = "kernel"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://cyw-wifi-bt/EULA.txt;md5=80c0478f4339af024519b3723023fe28"

SRC_URI = "git://github.com/NXP/imx-firmware.git;protocol=https;branch=master"
SRCREV = "484d38224fa2c26b8859a7bf20b7c4d49100f5bc"

S = "${WORKDIR}/git"

inherit allarch

CLEANBROKEN = "1"
ALLOW_EMPTY:${PN} = "1"

do_compile() {
	:
}

do_install() {
    install -d ${D}${sysconfdir}/firmware
    install -d ${D}${nonarch_base_libdir}/firmware/brcm

    # Install various flavors of Broadcom firmware provided by Murata:
    # - bcm4359-pcie
    install -m 0644 cyw-wifi-bt/*_CYW*/brcmfmac4359-pcie* ${D}${nonarch_base_libdir}/firmware/brcm
    install -m 0644 cyw-wifi-bt/*_CYW*/BCM4349B1*.hcd ${D}${sysconfdir}/firmware
}

PACKAGES =+ " \
    ${PN}-bcm4359-pcie \
"

FILES:${PN}-bcm4359-pcie = " \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4359-pcie.* \
    ${sysconfdir}/firmware/BCM4349B1_*.hcd \
"

RPROVIDES:${PN}-bcm4359-pcie = "linux-firmware-bcm4359-pcie"
