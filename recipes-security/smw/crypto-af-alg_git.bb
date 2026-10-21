# Copyright 2026 NXP

SUMMARY = "NXP i.MX CRYPTO AF_ALG"
DESCRIPTION = "NXP i.MX crypto af alg tool to perform symmetric operation"
HOMEPAGE = "https://github.com/nxp-imx/crypto_af_alg"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ef68f113b9400b1140be9b69208a816"

SRCBRANCH = "lf-6.18.37_2.1.0"
CRYPTO_AF_ALG_SRC ?= "git://github.com/nxp-imx/crypto_af_alg.git;protocol=https"
SRC_URI = "${CRYPTO_AF_ALG_SRC};branch=${SRCBRANCH}"

SRCREV = "a62b3ab1718ebfe9edd6747fb7539e4b3e43ca14"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
    oe_runmake DESTDIR=${D} install
}

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
