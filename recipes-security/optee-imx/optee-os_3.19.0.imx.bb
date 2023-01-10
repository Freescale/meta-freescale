# Copyright (C) 2017-2021 NXP

SUMMARY = "OPTEE OS"
DESCRIPTION = "OPTEE OS"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173"

DEPENDS = "python3-pyelftools-native u-boot-mkimage-native \
           python3-cryptography-native"
DEPENDS:append:toolchain-clang = " compiler-rt"

SRC_URI = "git://github.com/nxp-imx/imx-optee-os.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-core-Define-section-attributes-for-clang.patch \
           file://0006-allow-setting-sysroot-for-libgcc-lookup.patch \
           file://0007-allow-setting-sysroot-for-clang.patch \
           file://0010-add-note-GNU-stack-section.patch"
SRCBRANCH = "lf-5.15.71_2.2.0"
SRCREV = "00919403f040fad4f8603e605932281ff8451b1d"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit deploy python3native autotools features_check

REQUIRED_MACHINE_FEATURES = "optee"

# The platform flavor corresponds to the Yocto machine without the leading 'i'.
PLATFORM_FLAVOR                   = "${@d.getVar('MACHINE')[1:]}"
PLATFORM_FLAVOR:imx6qdlsabresd    = "mx6qsabresd"
PLATFORM_FLAVOR:imx6qdlsabreauto  = "mx6qsabreauto"
PLATFORM_FLAVOR:imx6qpdlsolox     = "mx6qsabresd"
PLATFORM_FLAVOR:mx6ul-nxp-bsp     = "mx6ulevk"
PLATFORM_FLAVOR:mx6ull-nxp-bsp    = "mx6ullevk"
PLATFORM_FLAVOR:mx6ulz-nxp-bsp    = "mx6ulzevk"
PLATFORM_FLAVOR:mx8mq-nxp-bsp     = "mx8mqevk"
PLATFORM_FLAVOR:mx8mm-nxp-bsp     = "mx8mmevk"
PLATFORM_FLAVOR:mx8mn-nxp-bsp     = "mx8mnevk"
PLATFORM_FLAVOR:mx8mnul-nxp-bsp   = "mx8mnevk"
PLATFORM_FLAVOR:mx8mp-nxp-bsp     = "mx8mpevk"
PLATFORM_FLAVOR:mx8mpul-nxp-bsp   = "mx8mpevk"
PLATFORM_FLAVOR:mx8qm-nxp-bsp     = "mx8qmmek"
PLATFORM_FLAVOR:mx8qxp-nxp-bsp    = "mx8qxpmek"
PLATFORM_FLAVOR:mx8dx-nxp-bsp     = "mx8dxmek"
PLATFORM_FLAVOR:mx8dxl-nxp-bsp    = "mx8dxlevk"
PLATFORM_FLAVOR:mx8ulp-nxp-bsp    = "mx8ulpevk"
PLATFORM_FLAVOR:mx93-nxp-bsp      = "mx93evk"

OPTEE_ARCH:arm     = "arm32"
OPTEE_ARCH:aarch64 = "arm64"

COMPILER ?= "gcc"
COMPILER:toolchain-clang = "clang"

# Optee-os can be built for 32 bits and 64 bits at the same time
# as long as the compilers are correctly defined.
# For 64bits, CROSS_COMPILE64 must be set
# When defining CROSS_COMPILE and CROSS_COMPILE64, we assure that
# any 32 or 64 bits builds will pass
EXTRA_OEMAKE = " \
    PLATFORM=imx-${PLATFORM_FLAVOR} \
    CROSS_COMPILE=${HOST_PREFIX} \
    CROSS_COMPILE64=${HOST_PREFIX} \
    CFG_TEE_TA_LOG_LEVEL=0 \
    CFG_TEE_CORE_LOG_LEVEL=0 \
    OPENSSL_MODULES=${STAGING_LIBDIR_NATIVE}/ossl-modules \
    COMPILER=${COMPILER} \
    -C ${S} O=${B} \
"

LDFLAGS[unexport] = "1"
CPPFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

CFLAGS += "--sysroot=${STAGING_DIR_HOST}"
CXXFLAGS += "--sysroot=${STAGING_DIR_HOST}"

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
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
