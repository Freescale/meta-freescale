# Copyright (C) 2017-2018 NXP

SUMMARY = "OPTEE OS"
DESCRIPTION = "OPTEE OS"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69663ab153298557a59c67a60a743e5b"

DEPENDS = "python-pycrypto-native u-boot-mkimage-native"

SRCBRANCH = "imx_4.9.123_imx8mm_ga"
OPTEE_OS_SRC ?= "git://source.codeaurora.org/external/imx/imx-optee-os.git;protocol=https"
SRC_URI = "${OPTEE_OS_SRC};branch=${SRCBRANCH}"
SRCREV = "abcc7c2135986a6d7d377f2f53c967cda0281351" 

S = "${WORKDIR}/git"

inherit deploy pythonnative

# The platform flavor corresponds to the Yocto machine without the leading 'i'.
PLATFORM_FLAVOR                 = "${@d.getVar('MACHINE')[1:]}"
PLATFORM_FLAVOR_imx6qpdlsolox   = "mx6qsabresd"
PLATFORM_FLAVOR_imx6ul7d        = "mx6ulevk"
PLATFORM_FLAVOR_imx6ull14x14evk = "mx6ullevk"

OPTEE_ARCH ?= "arm32"
OPTEE_ARCH_armv7a = "arm32"
OPTEE_ARCH_aarch64 = "arm64"

# Optee-os can be built for 32 bits and 64 bits at the same time
# as long as the compilers are correctly defined.
# For 64bits, CROSS_COMPILE64 must be set
# When defining CROSS_COMPILE and CROSS_COMPILE64, we assure that
# any 32 or 64 bits builds will pass
EXTRA_OEMAKE = "PLATFORM=imx PLATFORM_FLAVOR=${PLATFORM_FLAVOR} \
                CROSS_COMPILE=${HOST_PREFIX} \
                CROSS_COMPILE64=${HOST_PREFIX} \
                NOWERROR=1 \
                LDFLAGS= \
        "
CFLAGS += " --sysroot=${STAGING_DIR_HOST}"

do_compile () {
    unset LDFLAGS
    oe_runmake all CFG_TEE_TA_LOG_LEVEL=0
    ${OBJCOPY} -v -O binary ${B}/out/arm-plat-imx/core/tee.elf ${B}/out/arm-plat-imx/core/tee.bin
    if [ "${OPTEE_ARCH}" != "arm64" ]; then
        IMX_LOAD_ADDR=`cat ${B}/out/arm-plat-imx/core/tee-init_load_addr.txt`
        uboot-mkimage -A arm -O linux -C none -a ${IMX_LOAD_ADDR} -e ${IMX_LOAD_ADDR} \
            -d ${B}/out/arm-plat-imx/core/tee.bin ${B}/out/arm-plat-imx/core/uTee
    fi
}

do_install () {
    install -d ${D}/lib/firmware/
    install -m 644 ${B}/out/arm-plat-imx/core/tee.bin ${D}/lib/firmware/tee_${MACHINE}.bin
    ln -sf tee_${MACHINE}.bin ${D}/lib/firmware/tee.bin
    if [ "${OPTEE_ARCH}" != "arm64" ]; then
        install -m 644 ${B}/out/arm-plat-imx/core/uTee ${D}/lib/firmware/uTee_${MACHINE}
        ln -sf uTee_${MACHINE} ${D}/lib/firmware/uTee
    fi
    # Install the TA devkit
    install -d ${D}/usr/include/optee/export-user_ta/
    for f in ${B}/out/arm-plat-imx/export-ta_${OPTEE_ARCH}/*; do
        cp -aR $f ${D}/usr/include/optee/export-user_ta/
    done
}

do_deploy () {
    install -d ${DEPLOYDIR}
    for f in ${D}/lib/firmware/*; do
        cp $f ${DEPLOYDIR}
    done
}

addtask deploy after do_install before do_build

FILES_${PN} = "/lib/firmware/"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

PACKAGE_ARCH = "${MACHINE_ARCH}"
