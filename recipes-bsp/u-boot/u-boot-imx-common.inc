DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "imx_v2020.04_5.4.70_2.3.0"
SRC_URI = "git://source.codeaurora.org/external/imx/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "185bdaaaf5644319284566e5c340927d28954a1a"

DEPENDS += "flex-native bison-native bc-native dtc-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-imx_v2020.04_5.4.70_2.3.2"

BOOT_TOOLS = "imx-boot-tools"

