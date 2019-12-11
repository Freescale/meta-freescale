# Copyright 2019 NXP

SUMMARY = "NXP i.MX SECO firmware"
DESCRIPTION = "NXP IMX SECO firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=fb0303e4ee8b0e71c094171e2272bd44"

inherit fsl-eula-unpack deploy

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true "

SRC_URI[md5sum] = "cf8d6bbf93f0aa6e1049f1098a0f7e0f"
SRC_URI[sha256sum] = "b429ac58b2ea1771b3fdf7ae87bf1d37ccaf596bde01a5ecccae27b2145f6824"

do_compile[noexec] = "1"

do_install[noexec] = "1"

SECO_FIRMWARE_NAME ?= "mx8qm-ahab-container.img"
SECO_FIRMWARE_NAME_mx8qm  = "mx8qm-ahab-container.img"
SECO_FIRMWARE_NAME_mx8x   = "mx8qx-ahab-container.img"

addtask deploy after do_install
do_deploy () {
    # Deploy i.MX8 SECO firmware files
    install -m 0644 ${S}/firmware/seco/${SECO_FIRMWARE_NAME} ${DEPLOYDIR}
}

COMPATIBLE_MACHINE = "(mx8qm|mx8x)"
