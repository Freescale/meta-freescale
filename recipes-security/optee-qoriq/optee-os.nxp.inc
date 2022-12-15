# Copyright 2020-2021 NXP

SUMMARY = "OPTEE OS"
DESCRIPTION = "OPTEE OS"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173"

inherit deploy python3native autotools
DEPENDS = "python3-pycryptodome-native python3-pyelftools-native python3-pycryptodomex-native dtc-native"

SRC_URI = "git://github.com/nxp-qoriq/optee_os.git;protocol=https;nobranch=1"
SRCREV = "735d98806dc26fbeeecad7f5e60ffeab8170c67e"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build.${PLATFORM_FLAVOR}"

PLATFORM_FLAVOR ?= "${MACHINE}"

OPTEE_ARCH ?= "arm64"
OPTEE_ARCH:armv7a = "arm32"
OPTEE_ARCH:aarch64 = "arm64"

OPTEE_CORE_LOG_LEVEL ?= "1"
OPTEE_TA_LOG_LEVEL ?= "0"

# Optee-os can be built for 32 bits and 64 bits at the same time
# as long as the compilers are correctly defined.
# For 64bits, CROSS_COMPILE64 must be set
# When defining CROSS_COMPILE and CROSS_COMPILE64, we assure that
# any 32 or 64 bits builds will pass
EXTRA_OEMAKE = " \
	-C ${S} O=${B} \
	PLATFORM_FLAVOR=${PLATFORM_FLAVOR} \
	CROSS_COMPILE=${HOST_PREFIX} \
	CROSS_COMPILE64=${HOST_PREFIX} \
	CFG_WERROR=y \
	CFG_TEE_CORE_LOG_LEVEL=${OPTEE_CORE_LOG_LEVEL} \
	CFG_TEE_TA_LOG_LEVEL=${OPTEE_TA_LOG_LEVEL} \
"

do_compile() {
    unset LDFLAGS
    export CFLAGS="${CFLAGS} --sysroot=${STAGING_DIR_HOST}"
    oe_runmake all
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -m 644 ${B}/core/*.bin ${D}${nonarch_base_libdir}/firmware/

    # Install the TA devkit
    install -d ${D}${includedir}/optee/export-user_ta/

    for f in ${B}/export-ta_${OPTEE_ARCH}/*; do
        cp -aR $f ${D}${includedir}/optee/export-user_ta/
    done

    install -d ${D}${nonarch_base_libdir}/optee_armtz
    find ${B}/export-ta_${OPTEE_ARCH}/ta -name '*.ta' | while read name; do
        install -m 444 $name ${D}${nonarch_base_libdir}/optee_armtz/
    done
}

do_deploy() {
    install -d ${DEPLOYDIR}/optee
    install -m 644 ${D}${nonarch_base_libdir}/firmware/* ${DEPLOYDIR}/optee/
}
addtask deploy before do_build after do_install

FILES:${PN} = "${nonarch_base_libdir}/firmware/ ${nonarch_base_libdir}/optee_armtz/"
FILES:${PN}-staticdev = "/usr/include/optee/"
RDEPENDS:${PN}-dev += "${PN}-staticdev"

PACKAGE_ARCH = "${MACHINE_ARCH}"
