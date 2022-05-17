# Copyright (C) 2017-2021 NXP

SUMMARY = "OPTEE test"
HOMEPAGE = "http://www.optee.org/"

LICENSE = "BSD-2-Clause & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=daa2bcccc666345ab8940aab1315a4fa"

DEPENDS = "python3-pycryptodome-native python3-pycryptodomex-native optee-os optee-client openssl"

SRCBRANCH = "lf-5.15.5_1.0.0"

SRC_URI = "git://source.codeaurora.org/external/imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

SRCREV = "e9d8bf6ee121d6686e6e409c727caee76496bb86"

inherit python3native features_check

REQUIRED_MACHINE_FEATURES = "optee"

OPTEE_ARCH:arm     = "arm32"
OPTEE_ARCH:aarch64 = "arm64"

CFLAGS += "--sysroot=${STAGING_DIR_HOST}"
CXXFLAGS += "--sysroot=${STAGING_DIR_HOST}"

EXTRA_OEMAKE = " \
    TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta_${OPTEE_ARCH}/ \
    ARCH=${OPTEE_ARCH} \
    OPTEE_CLIENT_EXPORT=${STAGING_DIR_HOST}${exec_prefix} \
    CROSS_COMPILE_HOST=${HOST_PREFIX} \
    CROSS_COMPILE_TA=${HOST_PREFIX} \
    CROSS_COMPILE=${HOST_PREFIX} \
    -C ${S} O=${B} \
"

do_compile() {
    oe_runmake all
}
do_compile[cleandirs] = "${B}"

do_install () {
	install -d ${D}${bindir}
	install ${B}/xtest/xtest ${D}${bindir}

	install -d ${D}${nonarch_base_libdir}/optee_armtz
	find ${B}/ta -name '*.ta' | while read name; do
		install -m 444 $name ${D}${nonarch_base_libdir}/optee_armtz/
	done

	install -d ${D}${libdir}/tee-supplicant/plugins/
	install ${B}/supp_plugin/*plugin ${D}${libdir}/tee-supplicant/plugins/
}

FILES:${PN} += "${nonarch_base_libdir}/optee_armtz/ ${libdir}/tee-supplicant/plugins/"

RDEPENDS:${PN} = "optee-os"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
