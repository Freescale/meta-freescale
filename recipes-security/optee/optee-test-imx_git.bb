# Copyright (C) 2017-2018 NXP

SUMMARY = "OPTEE test"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=daa2bcccc666345ab8940aab1315a4fa"

DEPENDS = "optee-os-imx optee-client-imx python-pycrypto-native"

SRCBRANCH = "imx_4.9.123_imx8mm_ga"
OPTEE_TEST_SRC ?= "git://source.codeaurora.org/external/imx/imx-optee-test.git;protocol=https"
SRC_URI = " \
    ${OPTEE_TEST_SRC};branch=${SRCBRANCH} \
    file://0001-fix-build-failure-with-GCC-8.patch \
"
SRCREV = "b7b6c4d4af9607a3987988ae62b0957e659a32ef" 

S = "${WORKDIR}/git"

inherit pythonnative

ARCH = "arm"
ARCH_aarch64 = "arm64"

EXTRA_OEMAKE = " \
    TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta \
    OPTEE_CLIENT_EXPORT=${STAGING_DIR_HOST}${prefix} \
    ARCH=${ARCH} \
    CROSS_COMPILE_HOST=${TARGET_PREFIX} \
    CROSS_COMPILE_TA=${TARGET_PREFIX} \
    CROSS_COMPILE=${TARGET_PREFIX} \
    V=1 \
"

do_install () {
    install -D -p -m0755 ${S}/out/xtest/xtest ${D}${bindir}/xtest

    # install path should match the value set in optee-client/tee-supplicant
    # default TEEC_LOAD_PATH is /lib
    install -d ${D}${nonarch_base_libdir}/optee_armtz
    find ${S}/out/ta -name '*.ta' | while read name; do
        install -m 444 $name ${D}${nonarch_base_libdir}/optee_armtz/
    done
}

FILES_${PN} += "${nonarch_base_libdir}/optee_armtz/"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
