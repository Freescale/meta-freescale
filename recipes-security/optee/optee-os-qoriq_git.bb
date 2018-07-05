SUMMARY = "OP-TEE Trusted OS"
DESCRIPTION = "OPTEE OS"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=69663ab153298557a59c67a60a743e5b"

DEPENDS = "python-pycrypto-native"

inherit deploy pythonnative

SRCREV = "fe3b3da0ff1c108c848a7baafcb2788c506991ac"
SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/optee_os;nobranch=1 \
           file://0001-allow-setting-sysroot-for-libgcc-lookup.patch \
          "
S = "${WORKDIR}/git"

OPTEEMACHINE ?= "${MACHINE}"

EXTRA_OEMAKE = "PLATFORM=ls-${OPTEEMACHINE} CFG_ARM64_core=y \
                ARCH=arm \
                CROSS_COMPILE_core=${HOST_PREFIX} \
                CROSS_COMPILE_ta_arm64=${HOST_PREFIX} \
                NOWERROR=1 \
                LDFLAGS= \
                LIBGCC_LOCATE_CFLAGS=--sysroot=${STAGING_DIR_HOST} \
        "

OPTEE_ARCH_armv7a = "arm32"
OPTEE_ARCH_aarch64 = "arm64"

do_compile() {
    unset LDFLAGS
    oe_runmake all CFG_TEE_TA_LOG_LEVEL=0
}

do_install() {
    #install core on boot directory
    install -d ${D}/lib/firmware/

    ${OBJCOPY} -v -O binary ${B}/out/arm-plat-ls/core/tee.elf   ${B}/out/arm-plat-ls/core/tee.bin
    install -m 644 ${B}/out/arm-plat-ls/core/tee.bin ${D}/lib/firmware/tee_${MACHINE}.bin
    #install TA devkit
    install -d ${D}/usr/include/optee/export-user_ta/

    for f in  ${B}/out/arm-plat-ls/export-ta_${OPTEE_ARCH}/* ; do
        cp -aR  $f  ${D}/usr/include/optee/export-user_ta/
    done
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_deploy() {
        install -d ${DEPLOYDIR}/optee
        for f in ${D}/lib/firmware/*; do
            install -m 644 $f ${DEPLOYDIR}/optee/tee_${MACHINE}.bin
        done
}

addtask deploy before do_build after do_install

FILES_${PN} = "/lib/firmware/"
FILES_${PN}-dev = "/usr/include/optee"

INSANE_SKIP_${PN}-dev = "staticdev"

INHIBIT_PACKAGE_STRIP = "1"
COMPATIBLE_MACHINE = "(ls1043ardb|ls1046ardb|ls1012ardb)"
