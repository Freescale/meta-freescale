# Copyright (C) 2019-2020 NXP

SUMMARY = "NXP i.MX SECO firmware"
DESCRIPTION = "NXP IMX SECO firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=228c72f2a91452b8a03c4cab30f30ef9"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

SRC_URI[md5sum] = "22a47e14e3f2e713b4b1b2b7ff768b11"
SRC_URI[sha256sum] = "52ba07633e0f8707d8c26724b5cd03ef96444c8de1e0e134acac50acacf3e7dd"

do_compile[noexec] = "1"

do_install[noexec] = "1"

addtask deploy after do_install
do_deploy () {
    # Deploy i.MX8 SECO firmware files
    install -m 0644 ${S}/firmware/seco/${SECO_FIRMWARE_NAME} ${DEPLOYDIR}
}

COMPATIBLE_MACHINE = "(mx8qm|mx8qxp|mx8qxpc0|mx8phantomdxl|mx8dxl)"
