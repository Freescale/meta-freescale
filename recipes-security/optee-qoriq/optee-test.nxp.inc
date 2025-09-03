# Copyright 2020-2021,2025 NXP
require recipes-security/optee-imx/optee-test-fslc.inc

DEPENDS:remove = "optee-client optee-os-tadevkit"
DEPENDS:append = "optee-client-qoriq optee-os-qoriq-tadevkit openssl"

SRC_URI:remove = "git://github.com/OP-TEE/optee_test.git;branch=master;protocol=https"
SRC_URI:remove = "file://run-ptest"
SRC_URI:prepend = "${OPTEE_TEST_SRC};branch=${OPTEE_TEST_BRANCH} "

OPTEE_TEST_SRC ?= "git://github.com/nxp-qoriq/optee_test.git;protocol=https"

CFLAGS += " -Wno-error=unterminated-string-initialization"

COMPATIBLE_MACHINE = "(qoriq-arm64)"
