# Copyright 2020-2021,2025 NXP
require recipes-security/optee-imx/optee-os-fslc.inc

DEPENDS:append = " dtc-native"

# The patch same as imx-optee, so point FILESEXTRAPATHS to optee-imx/optee-client,
# avoid duplicate copy files to optee-qoriq
FILESEXTRAPATHS:prepend := "${THISDIR}/../optee-imx/optee-os:"

SRC_URI:remove = "git://github.com/OP-TEE/optee_os.git;branch=master;protocol=https"
SRC_URI:prepend = "${OPTEE_OS_SRC};branch=${OPTEE_OS_BRANCH} "
SRC_URI:append = " file://0007-allow-setting-sysroot-for-clang.patch"

OPTEE_OS_SRC ?= "git://github.com/nxp-qoriq/optee_os.git;protocol=https"

REQUIRED_MACHINE_FEATURES = "optee"

inherit features_check

OPTEEMACHINE = "ls-${MACHINE}"
OPTEEMACHINE:ls1088ardb-pb   = "ls-ls1088ardb"
OPTEEMACHINE:ls1046afrwy     = "ls-ls1046ardb"
OPTEEMACHINE:lx2162aqds      = "ls-lx2160aqds"
OPTEEMACHINE:lx2160ardb-rev2 = "ls-lx2160ardb"

EXTRA_OEMAKE:append = " \
    CFG_TEE_TA_LOG_LEVEL=0 \
    CFG_TEE_CORE_LOG_LEVEL=1 \
"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
