SUMMARY = "Dummy package for SoCs lacking imx-test package"
DESCRIPTION = "Dummy package for SoCs lacking imx-test package"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch

ALLOW_EMPTY_${PN} = "1"
