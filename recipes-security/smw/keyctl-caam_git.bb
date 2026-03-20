# Copyright 2020-2022 NXP

SUMMARY = "NXP i.MX CAAM Keyctl"
DESCRIPTION = "NXP i.MX keyctl tool to manage CAAM Keys"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8636bd68fc00cc6a3809b7b58b45f982"

SRCBRANCH = "lf-6.18.2_1.0.0"
SRC_URI = "git://github.com/nxp-imx/keyctl_caam.git;protocol=https;branch=${SRCBRANCH}"

SRCREV = "71cb18f17d766145217a5517748e37e250b055bf"

DEPENDS = " openssl"

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OEMAKE = " OPENSSL_PATH=${STAGING_INCDIR}"

do_install () {
	oe_runmake DESTDIR=${D} install
}

COMPATIBLE_MACHINE = "(imx-generic-bsp|qoriq)"

