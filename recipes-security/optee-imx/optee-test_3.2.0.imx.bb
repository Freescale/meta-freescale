# Copyright (C) 2017-2018 NXP

SUMMARY = "OPTEE test"
HOMEPAGE = "http://www.optee.org/"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=daa2bcccc666345ab8940aab1315a4fa"

DEPENDS = "optee-os optee-client python3-pycrypto-native openssl"
inherit python3native

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRCBRANCH = "imx_4.19.35_1.1.0"
OPTEE_TEST_SRC ?= "git://source.codeaurora.org/external/imx/imx-optee-test.git;protocol=https"

SRC_URI = "${OPTEE_TEST_SRC};branch=${SRCBRANCH} \
           file://0003-sock_server-fix-compilation-against-musl-sys-errno.h.patch \
           file://0004-build-ignore-declaration-after-statement-warnings.patch \
           file://0005-benchmark_1000-fix-compilation-against-musl-uint.patch \
           file://0006-regression_8100-use-null-terminated-strings-with-fil.patch \
           file://0001-use-python3-instead-of-python.patch \
"

S = "${WORKDIR}/git"

SRCREV = "b7114b828b82f2c0eec124ed424eff1230cc5319"



do_compile () {
    if [ ${DEFAULTTUNE} = "aarch64" ];then
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
    oe_runmake V=1
}

do_install () {
    install -d ${D}/usr/bin
    install ${S}/out/xtest/xtest ${D}/usr/bin/

    install -d ${D}/lib/optee_armtz
    find ${S}/out/ta -name '*.ta' | while read name; do
    install -m 444 $name ${D}/lib/optee_armtz/
    done

}

FILES_${PN} = "/usr/bin/ /lib*/optee_armtz/"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
