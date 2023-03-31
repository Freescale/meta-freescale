DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "imx_v2020.04_5.4.24_2.1.0"
SRC_URI = "git://github.com/nxp-imx/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "4979a99482f7e04a3c1f4fb55e3182395ee8f710"

DEPENDS += "flex-native bison-native bc-native dtc-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-${SRCBRANCH}"

BOOT_TOOLS = "imx-boot-tools"

