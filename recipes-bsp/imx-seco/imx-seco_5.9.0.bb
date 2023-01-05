# Copyright 2019-2022 NXP

SUMMARY = "NXP i.MX SECO firmware"
DESCRIPTION = "Firmware for i.MX Security Controller Subsystem"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

SRC_URI[md5sum] = "b722a534c4d3cc90270e05eaa812514d"
SRC_URI[sha256sum] = "c3bd761f457e939035b01a0ab36e79064a2a1bc6c3cdb3cd847f7f38df0964df"


do_compile[noexec] = "1"

do_install[noexec] = "1"

addtask deploy after do_install
do_deploy () {
    # Deploy i.MX8 SECO firmware files
    install -m 0644 ${S}/firmware/seco/${SECO_FIRMWARE_NAME} ${DEPLOYDIR}
}

COMPATIBLE_MACHINE = "(mx8qm-nxp-bsp|mx8qxp-nxp-bsp|mx8dxl-nxp-bsp|mx8dx-nxp-bsp)"
