# Copyright 2020-2021 NXP

SUMMARY = "OPTEE test"
HOMEPAGE = "http://www.optee.org/"

LICENSE = "BSD & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=daa2bcccc666345ab8940aab1315a4fa"

DEPENDS = "python3-pycryptodome-native python3-pycryptodomex-native openssl"
inherit python3native cmake

SRC_URI = "git://github.com/nxp-qoriq/optee_test.git;protocol=https;nobranch=1"
SRCREV = "69722dab8c1f2683e30e0ee3b536053367e37aad"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

TA_DEV_KIT_DIR ?= "${STAGING_INCDIR}/optee/export-user_ta"
OPTEE_CLIENT_EXPORT ?= "${STAGING_DIR_HOST}${prefix}"

EXTRA_OEMAKE = " \
    TA_DEV_KIT_DIR=${TA_DEV_KIT_DIR} \
    OPTEE_CLIENT_EXPORT=${OPTEE_CLIENT_EXPORT} \
    CROSS_COMPILE_HOST=${HOST_PREFIX} \
    CROSS_COMPILE_TA=${HOST_PREFIX} \
    OPTEE_OPENSSL_EXPORT=${STAGING_INCDIR}/ \
    -C ${S} O=${B} \
"

EXTRA_OECMAKE = " \
    -DOPTEE_TEST_SDK=${TA_DEV_KIT_DIR} \
"

do_compile() {
    export CXXFLAGS="${CXXFLAGS} --sysroot=${STAGING_DIR_HOST}"
    oe_runmake xtest
    oe_runmake ta
    oe_runmake test_plugin
}

do_install() {
	install -d ${D}${bindir}/
	install ${B}/xtest/xtest ${D}${bindir}/

	install -d ${D}${nonarch_base_libdir}/optee_armtz
	find ${B}/ta -name '*.ta' | while read name; do
		install -m 444 $name ${D}${nonarch_base_libdir}/optee_armtz/
	done

	install -d ${D}${libdir}/tee-supplicant/plugins/
	find ${B}/supp_plugin -name '*.plugin' | while read name; do
		install -m 755 $name ${D}${libdir}/tee-supplicant/plugins/
	done
}

FILES:${PN} += "${nonarch_base_libdir} ${libdir}/tee-supplicant/plugins/"

DEBUG_OPTIMIZATION:append = " -Wno-error=maybe-uninitialized -Wno-deprecated-declarations"
FULL_OPTIMIZATION:append = " -Wno-error=maybe-uninitialized -Wno-deprecated-declarations"

PACKAGE_ARCH = "${MACHINE_ARCH}"
