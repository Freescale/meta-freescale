# Copied from meta-imx/meta-imx-bsp/recipes-security/optee/optee-test-imx.inc.
# See: https://github.com/nxp-imx/imx-manifest/blob/imx-linux-walnascar/imx-6.12.20-2.0.0.xml#L37
require optee-test-fslc.inc

DEPENDS += "openssl"

FILESEXTRAPATHS:prepend := "${THISDIR}/optee-test:"

SRC_URI:remove = "git://github.com/OP-TEE/optee_test.git;branch=master;protocol=https"
SRC_URI:prepend = "${OPTEE_TEST_SRC};branch=${SRCBRANCH} "

OPTEE_TEST_SRC ?= "git://github.com/nxp-imx/imx-optee-test.git;protocol=https"

EXTRA_OEMAKE:append:libc-musl = " OPTEE_OPENSSL_EXPORT=${STAGING_INCDIR}"
CFLAGS:append:libc-musl = " -Wno-error=deprecated-declarations"
CFLAGS += " -Wno-error=unterminated-string-initialization"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
