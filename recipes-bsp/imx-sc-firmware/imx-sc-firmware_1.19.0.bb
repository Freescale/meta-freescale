# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2017-2024,2026 NXP

SUMMARY = "i.MX System Controller firmware"
DESCRIPTION = "System Controller Unit (SCU) firmware for i.MX8 QuadMax/QuadXPlus SoCs."
HOMEPAGE = "https://www.nxp.com/"
SECTION = "bsp"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

inherit fsl-eula-unpack pkgconfig deploy

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "39814e0f7d83bc1ab0a7a4e03cc20a0ddd4ab00e96fbbc2d059b791d117a6917"
IMX_SRCREV_ABBREV = "1c41cd8"

S = "${UNPACKDIR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}"

BOARD_TYPE ?= "mek"
SC_FIRMWARE_NAME ?= "INVALID"
SC_FIRMWARE_NAME:mx8qm-generic-bsp = "mx8qm-${BOARD_TYPE}-scfw-tcm.bin"
SC_FIRMWARE_NAME:mx8qxp-generic-bsp = "mx8qx-${BOARD_TYPE}-scfw-tcm.bin"
SC_FIRMWARE_NAME:mx8dxl-generic-bsp = "mx8dxl-${BOARD_TYPE}-scfw-tcm.bin"
SC_FIRMWARE_NAME:mx8dx-generic-bsp = "mx8dx-${BOARD_TYPE}-scfw-tcm.bin"

symlink_name = "scfw_tcm.bin"

BOOT_TOOLS = "imx-boot-tools"

LDFLAGS:remove = "-fuse-ld=lld"
LDFLAGS:append = " -fuse-ld=bfd"

do_compile[noexec] = "1"

do_install[noexec] = "1"

do_deploy() {
    install -Dm 0644 ${S}/${SC_FIRMWARE_NAME} ${DEPLOYDIR}/${BOOT_TOOLS}/${SC_FIRMWARE_NAME}
    ln -sf ${SC_FIRMWARE_NAME} ${DEPLOYDIR}/${BOOT_TOOLS}/${symlink_name}
}
addtask deploy after do_install

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(mx8qm-generic-bsp|mx8qxp-generic-bsp|mx8dxl-generic-bsp|mx8dx-generic-bsp)"
