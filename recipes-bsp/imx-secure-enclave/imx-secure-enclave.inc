# Copyright 2025 NXP

SUMMARY = "NXP i.MX SECURE ENCLAVE library"
DESCRIPTION = "NXP IMX SECURE ENCLAVE library"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8636bd68fc00cc6a3809b7b58b45f982"

DEPENDS = "openssl"

SRC_URI = "${SECURE_ENCLAVE_LIB_SRC};branch=${SRCBRANCH}"
SECURE_ENCLAVE_LIB_SRC ?= "git://github.com/NXP/imx-secure-enclave.git;protocol=https"
SRCBRANCH = "lf-6.12.20_2.0.0"
SRCREV = "2c4dd2a73265d60b83d288a4b3936a773790c139"

PLAT ?= "UNDEFINED"

EXTRA_OEMAKE = "PLAT=${PLAT} OPENSSL_PATH=${STAGING_INCDIR} MBEDTLS_PATH=${STAGING_DIR_HOST}${datadir}/mbedtls-source"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
	oe_runmake -C ${S} DESTDIR=${D} install_tests
}

FILES:${PN} += "${datadir}/se"
RDEPENDS:${PN} += "bash"
