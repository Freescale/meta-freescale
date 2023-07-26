# Copyright (C) 2017-2021 NXP

SUMMARY = "OPTEE OS"
DESCRIPTION = "OPTEE OS"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173"

require optee-fslc.inc

DEPENDS += "python3-pyelftools-native u-boot-mkimage-native"
DEPENDS:append:toolchain-clang = " compiler-rt"

inherit deploy autotools

# Optee-os can be built for 32 bits and 64 bits at the same time
# as long as the compilers are correctly defined.
# For 64bits, CROSS_COMPILE64 must be set
# When defining CROSS_COMPILE and CROSS_COMPILE64, we assure that
# any 32 or 64 bits builds will pass
EXTRA_OEMAKE += " \
    PLATFORM=imx-${PLATFORM_FLAVOR} \
    CROSS_COMPILE=${HOST_PREFIX} \
    CROSS_COMPILE64=${HOST_PREFIX} \
    CFLAGS32=--sysroot=${STAGING_DIR_HOST} \
    CFLAGS64=--sysroot=${STAGING_DIR_HOST} \
    CFG_TEE_TA_LOG_LEVEL=0 \
    CFG_TEE_CORE_LOG_LEVEL=0 \
"

LDFLAGS[unexport] = "1"
CPPFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure[noexec] = "1"

do_compile:prepend() {
    PLAT_LIBGCC_PATH=$(${CC} -print-libgcc-file-name)
}

do_compile:arm () {
    oe_runmake all uTee
}

do_compile:aarch64 () {
    oe_runmake all
}
do_compile[cleandirs] = "${B}"

do_deploy () {
    install -d ${DEPLOYDIR}
    cp ${B}/core/tee-raw.bin ${DEPLOYDIR}/tee.${PLATFORM_FLAVOR}.bin
    ln -sf tee.${PLATFORM_FLAVOR}.bin ${DEPLOYDIR}/tee.bin
}

do_deploy:append:arm () {
    cp ${B}/core/uTee ${DEPLOYDIR}/uTee-${OPTEE_BIN_EXT}
}

do_install () {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -m 644 ${B}/core/*.bin ${D}${nonarch_base_libdir}/firmware/

    # Install embedded TAs
    install -d ${D}${nonarch_base_libdir}/optee_armtz/
    install -m 444 ${B}/ta/*/*.ta ${D}${nonarch_base_libdir}/optee_armtz/

    # Install the TA devkit
    install -d ${D}${includedir}/optee/export-user_ta_${OPTEE_ARCH}/
    cp -aR ${B}/export-ta_${OPTEE_ARCH}/* \
        ${D}${includedir}/optee/export-user_ta_${OPTEE_ARCH}/
}

addtask deploy after do_compile before do_install

FILES:${PN} = "${nonarch_base_libdir}/firmware/ ${nonarch_base_libdir}/optee_armtz/"
FILES:${PN}-staticdev = "${includedir}/optee/"
RDEPENDS:${PN}-dev += "${PN}-staticdev"

PACKAGE_ARCH = "${MACHINE_ARCH}"
