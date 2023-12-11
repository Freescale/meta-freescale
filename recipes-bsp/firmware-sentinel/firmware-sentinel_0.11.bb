# Copyright 2021-2023 NXP
SUMMARY = "NXP i.MX Sentinel firmware"
DESCRIPTION = "Firmware for i.MX Sentinel Security Controller"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=db4762b09b6bda63da103963e6e081de"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[md5sum] = "339011b6b199151d835c03089a3c2221"
SRC_URI[sha256sum] = "269480417a8ae9aa4cc4101ab947287fc33455a931021dbdc4d9badb5212bceb"

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
