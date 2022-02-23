# Copyright (C) 2019-2021 NXP

SUMMARY = "NXP i.MX SECO firmware"
DESCRIPTION = "NXP i.MX Security Controller firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=03bcadc8dc0a788f66ca9e2b89f56c6f"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

SRC_URI[md5sum] = "38d298cda9ea91db945b266bd6774e07"
SRC_URI[sha256sum] = "ed65381fac7a17a7594099191952821ed3f3f54c944937926dc19f82ecd8fef5"

do_compile[noexec] = "1"

do_install[noexec] = "1"

addtask deploy after do_install
do_deploy () {
    # Deploy i.MX8 SECO firmware files
    install -m 0644 ${S}/firmware/seco/${SECO_FIRMWARE_NAME} ${DEPLOYDIR}
}

COMPATIBLE_MACHINE = "(mx8qm-nxp-bsp|mx8qxp-nxp-bsp|mx8dxl-nxp-bsp|mx8dx-nxp-bsp)"
