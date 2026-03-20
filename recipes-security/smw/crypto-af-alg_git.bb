# Copyright 2026 NXP

SUMMARY = "NXP i.MX CRYPTO AF_ALG"
DESCRIPTION = "NXP i.MX crypto af alg tool to perform symmetric operation"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ef68f113b9400b1140be9b69208a816"

SRCBRANCH = "lf-6.18.2_1.0.0"
CRYPTO_AF_ALG_SRC ?= "git://github.com/nxp-imx/crypto_af_alg.git;protocol=https"
SRC_URI = "${CRYPTO_AF_ALG_SRC};branch=${SRCBRANCH}"

SRCREV = "2fb11a600af90c80b8bb23368d1d6831c5deb973"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
	oe_runmake DESTDIR=${D} install
}

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
