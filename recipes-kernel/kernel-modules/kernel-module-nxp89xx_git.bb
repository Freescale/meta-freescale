SUMMARY = "NXP Wi-Fi driver for module 88w8801/8987/8997/9098 IW416/612"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=ab04ac0f249af12befccb94447c08b77"

SRCBRANCH = "lf-6.1.22_2.0.0"
MRVL_SRC ?= "git://github.com/nxp-imx/mwifiex.git;protocol=https"
SRC_URI = "${MRVL_SRC};branch=${SRCBRANCH}"
SRCREV = "f1382ccbd34fc22daf504e798745f6cddb702b82"

S = "${WORKDIR}/git/mxm_wifiex/wlan_src"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

