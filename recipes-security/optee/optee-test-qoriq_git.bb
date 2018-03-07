SUMMARY = "OP-TEE sanity testsuite"
HOMEPAGE = "https://github.com/qoriq-open-source/optee_test"

LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.md;md5=daa2bcccc666345ab8940aab1315a4fa"

DEPENDS = "optee-client-qoriq optee-os-qoriq python-pycrypto-native"

inherit pythonnative

SRC_URI = "git://github.com/qoriq-open-source/optee_test.git;nobranch=1 \
          "
S = "${WORKDIR}/git"

SRCREV = "a54952babb83d801148867bd639a24ff6cd36f00"

OPTEE_CLIENT_EXPORT = "${STAGING_DIR_HOST}${prefix}"
TEEC_EXPORT         = "${STAGING_DIR_HOST}${prefix}"
TA_DEV_KIT_DIR      = "${STAGING_INCDIR}/optee/export-user_ta"

EXTRA_OEMAKE = " TA_DEV_KIT_DIR=${TA_DEV_KIT_DIR} \
                 OPTEE_CLIENT_EXPORT=${OPTEE_CLIENT_EXPORT} \
                 TEEC_EXPORT=${TEEC_EXPORT} \
                 CROSS_COMPILE_HOST=${TARGET_PREFIX} \
                 CROSS_COMPILE_TA=${TARGET_PREFIX} \
                 V=1 \
               "

do_compile() {
    # Top level makefile doesn't seem to handle parallel make gracefully
    oe_runmake xtest
    oe_runmake ta
}

do_install () {
    install -D -p -m0755 ${S}/out/xtest/xtest ${D}${bindir}/xtest

    # install path should match the value set in optee-client/tee-supplicant
    # default TEEC_LOAD_PATH is /lib
    mkdir -p ${D}/lib/optee_armtz/
    install -D -p -m0444 ${S}/out/ta/*/*.ta ${D}/lib/optee_armtz/
}

FILES_${PN} += "/lib/optee_armtz/"

# Imports machine specific configs from staging to build
PACKAGE_ARCH = "${MACHINE_ARCH}"
