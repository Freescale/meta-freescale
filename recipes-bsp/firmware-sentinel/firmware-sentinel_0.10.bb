# Copyright 2021-2023 NXP
SUMMARY = "NXP i.MX Sentinel firmware"
DESCRIPTION = "Firmware for i.MX Sentinel Security Controller"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=63a38e9f392d8813d6f1f4d0d6fbe657"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[md5sum] = "a92e272d665a3b3bb9281253d5eca69f"
SRC_URI[sha256sum] = "be862b62c849510cce08ec24c1ddf53d826458e326e5a7f09c4b35092d6f9950"

do_compile[noexec] = "1"

do_install() {
    install -Dm 0644 ${S}/${SECO_FIRMWARE_NAME} ${D}${nonarch_base_libdir}/firmware/imx/ele/${SECO_FIRMWARE_NAME}
}

do_deploy() {
    install -m 0644 ${S}/${SECO_FIRMWARE_NAME} ${DEPLOYDIR}
}
addtask deploy after do_compile before do_build

FILES:${PN} += "${nonarch_base_libdir}/firmware/imx/ele/${SECO_FIRMWARE_NAME}"

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp|mx9-nxp-bsp)"
