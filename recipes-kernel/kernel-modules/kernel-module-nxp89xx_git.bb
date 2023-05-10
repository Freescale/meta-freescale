SUMMARY = "NXP Wi-Fi driver for module 88w8997/8987"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=ab04ac0f249af12befccb94447c08b77"

SRCBRANCH = "lf-6.1.1_1.0.0"
MRVL_SRC ?= "git://github.com/nxp-imx/mwifiex.git;protocol=https"
SRC_URI = "${MRVL_SRC};branch=${SRCBRANCH}"
SRCREV = "98e5b28b1a7afea9dbded4067e93bfd584531a79"

S = "${WORKDIR}/git/mxm_wifiex/wlan_src"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

