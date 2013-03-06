SUMMARY = "Dummy package for SoCs lacking imx-test package"
DESCRIPTION = "Dummy package for SoCs lacking imx-test package"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

inherit allarch

ALLOW_EMPTY_${PN} = "1"
