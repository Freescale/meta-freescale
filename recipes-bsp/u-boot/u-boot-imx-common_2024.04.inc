DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/nxp-imx/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf_v2024.04"
LOCALVERSION ?= "-imx_v2024.04_6.6.23-2.0.0"
SRCREV = "674440bc73e1dd483b84269cccfad89ab40af424"

DEPENDS += " \
    bc-native \
    bison-native \
    dtc-native \
    flex-native \
    gnutls-native \
    xxd-native \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit fsl-u-boot-localversion

BOOT_TOOLS = "imx-boot-tools"
