# Copyright (C) 2019 NXP

SUMMARY = "OPTEE test"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=daa2bcccc666345ab8940aab1315a4fa"

DEPENDS = "optee-os optee-client python3-pycrypto-native openssl"
inherit pythonnative

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRCBRANCH = "master"
OPTEE_TEST_SRC ?= "git://github.com/OP-TEE/optee_test.git;protocol=https"
SRCREV = "3.7.0"
SRC_URI = "${OPTEE_TEST_SRC};branch=${SRCBRANCH}"
SRC_URI_append = " file://0001-fix-build-failure-with-GCC-9-v2.patch"

S = "${WORKDIR}/git"

do_compile () {
    if [ ${TUNE_ARCH} = "aarch64" ];then
        export TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta_arm64/
        export ARCH=arm64
    else
        export TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta_arm32/
        export ARCH=arm
    fi
    export OPTEE_CLIENT_EXPORT=${STAGING_DIR_HOST}/usr
    export CROSS_COMPILE_HOST=${HOST_PREFIX}
    export CROSS_COMPILE_TA=${HOST_PREFIX}
    export CROSS_COMPILE=${HOST_PREFIX}
    export OPTEE_OPENSSL_EXPORT=${STAGING_INCDIR}/
    oe_runmake -C ${S} O=${B}
}

do_install () {
    install -d ${D}/usr/bin
    install ${B}/xtest/xtest ${D}/usr/bin/

    install -d ${D}/lib/optee_armtz
    find ${B}/ta -name '*.ta' | while read name; do
    install -m 444 $name ${D}/lib/optee_armtz/
    done
}

FILES_${PN} = "/usr/bin/ /lib*/optee_armtz/"

COMPATIBLE_MACHINE = "(imx)"
