DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/nxp-imx/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf_v2022.04"
LOCALVERSION ?= "-imx_v2022.04_5.15.52-2.1.0"
SRCREV = "16e8414193985cf7a171f7b79e12ff30700157ba"

DEPENDS += " \
    bc-native \
    bison-native \
    dtc-native \
    flex-native \
    gnutls-native \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit fsl-u-boot-localversion

BOOT_TOOLS = "imx-boot-tools"
