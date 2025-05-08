# Copyright 2019-2024 NXP

SUMMARY = "NXP i.MX SECO firmware"
DESCRIPTION = "Firmware for i.MX Security Controller Subsystem"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ca53281cc0caa7e320d4945a896fb837"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"

SRC_URI[sha256sum] = "bd8dc01966076836aabff53f2463295294166595006e1db430db21b6ffa6b667"
IMX_SRCREV_ABBREV = "0333596"

S = "${WORKDIR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}"

do_compile[noexec] = "1"

do_install[noexec] = "1"

addtask deploy after do_install
do_deploy () {
    # Deploy i.MX8 SECO firmware files
    install -m 0644 ${S}/firmware/seco/${SECO_FIRMWARE_NAME} ${DEPLOYDIR}
}

COMPATIBLE_MACHINE = "(mx8qm-generic-bsp|mx8qxp-generic-bsp|mx8dxl-generic-bsp|mx8dx-generic-bsp)"
