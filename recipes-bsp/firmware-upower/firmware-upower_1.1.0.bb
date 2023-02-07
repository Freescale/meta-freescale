# Copyright 2021-2022 NXP
DESCRIPTION = "NXP i.MX uPower firmware"
LICENSE = "Proprietary"
SECTION = "BSP"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

S = "${WORKDIR}/${PN}-${PV}"

inherit fsl-eula-unpack pkgconfig deploy

SRC_URI[md5sum] = "d2cbe8d2f8fa170e5d48c599c4caac5e"
SRC_URI[sha256sum] = "c02595917744769abe810107a08506e4055b8077b5fc4ed17c353b833756c8b0"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install[noexec] = "1"

BOOT_TOOLS = "imx-boot-tools"

do_deploy () {
    # Deploy the related firmware to be package by imx-boot
    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
    install -m 0644 ${S}/${UPOWER_FIRMWARE_NAME}  ${DEPLOYDIR}/${BOOT_TOOLS}/upower.bin
}
addtask deploy after do_install before do_build

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp)"
