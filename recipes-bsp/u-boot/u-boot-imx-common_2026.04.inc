DESCRIPTION = "The U-Boot bootloader that NXP maintains for the i.MX reference boards. It carries the SoC and board support that the mainline U-Boot tree does not yet provide."

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS += "\
    bc-native \
    bison-native \
    dtc-native \
    flex-native \
    gnutls-native \
    xxd-native \
"

SRC_URI = "${UBOOT_SRC};branch=${SRCBRANCH} \
           file://0001-Add-support-for-OpenSSL-Provider-API.patch \
           file://disable-efi-capsule-update.cfg \
"
UBOOT_SRC ?= "git://github.com/nxp-imx/uboot-imx.git;protocol=https"
SRCBRANCH = "lf_v2026.04"
LOCALVERSION ?= "-${SRCBRANCH}"
SRCREV = "bb41975aeb4cbd153b49c9fd62da1d64cd65968d"

B = "${WORKDIR}/build"

inherit fsl-u-boot-localversion pkgconfig

BOOT_TOOLS = "imx-boot-tools"
