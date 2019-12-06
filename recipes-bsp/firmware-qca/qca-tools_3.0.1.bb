# Copyright 2018 NXP

require firmware-qca.inc

SUMMARY = "Qualcomm Wi-Fi and Bluetooth tools"
DESCRIPTION = "Qualcomm Wi-Fi and Bluetooth tools for modules such as QCA6174A and QCA9377"
LICENSE = "Proprietary"

DEPENDS = "libnl zlib"

FCC_TOOLS_FOLDER ?= "qca9377_qca6174_arm32"
FCC_TOOLS_FOLDER_aarch64 = "qca9377_qca6174_arm64"

do_install() {
    install -d ${D}${sbindir}/fcc_tools
    cp -r ${S}/fcc_tools/${FCC_TOOLS_FOLDER} ${D}${sbindir}/fcc_tools
}

COMPATIBLE_HOST = '(aarch64|arm).*-linux'
COMPATIBLE_HOST_libc-musl = 'null'
