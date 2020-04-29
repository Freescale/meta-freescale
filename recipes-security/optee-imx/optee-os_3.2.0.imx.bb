# Copyright (C) 2017-2018 NXP

SUMMARY = "OPTEE OS"
DESCRIPTION = "OPTEE OS"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69663ab153298557a59c67a60a743e5b"

inherit deploy pythonnative autotools
DEPENDS = "python-pycrypto-native u-boot-mkimage-native"

SRCBRANCH = "imx_4.14.78_1.0.0_ga"
OPTEE_OS_SRC ?= "git://source.codeaurora.org/external/imx/imx-optee-os.git;protocol=https"
SRC_URI = "${OPTEE_OS_SRC};branch=${SRCBRANCH}"
SRCREV = "6a52487eb0ff664e4ebbd48497f0d3322844d51d"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build.${PLATFORM_FLAVOR}"

# The platform flavor corresponds to the Yocto machine without the leading 'i'.
PLATFORM_FLAVOR                 = "${@d.getVar('MACHINE')[1:]}"
PLATFORM_FLAVOR_imx6qpdlsolox   = "mx6qsabresd"
PLATFORM_FLAVOR_imx6ul7d        = "mx6ulevk"
PLATFORM_FLAVOR_imx6ull14x14evk = "mx6ullevk"
PLATFORM_FLAVOR_imx6ull9x9evk   = "mx6ullevk"
PLATFORM_FLAVOR_imx6ulz14x14evk = "mx6ulzevk"
PLATFORM_FLAVOR_mx8mm   = "mx8mmevk"

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
                O=${B} \
                CFG_SECURE_DATA_PATH=y \
                CFG_TEE_SDP_MEM_BASE=0xCC000000 \
                CFG_TEE_SDP_MEM_SIZE=0x02000000 \
                CFG_TEE_SDP_NONCACHE=y \
                "


do_compile () {
    unset LDFLAGS
    export CFLAGS="${CFLAGS} --sysroot=${STAGING_DIR_HOST}"
    oe_runmake -C ${S} all CFG_TEE_TA_LOG_LEVEL=0
}


do_deploy () {
    install -d ${DEPLOYDIR}
    ${TARGET_PREFIX}objcopy -O binary ${B}/core/tee.elf ${DEPLOYDIR}/tee.${PLATFORM_FLAVOR}.bin

    if [ "${OPTEE_ARCH}" != "arm64" ]; then
        IMX_LOAD_ADDR=`cat ${B}/core/tee-init_load_addr.txt` && \
        uboot-mkimage -A arm -O linux -C none -a ${IMX_LOAD_ADDR} -e ${IMX_LOAD_ADDR} \
            -d ${DEPLOYDIR}/tee.${PLATFORM_FLAVOR}.bin ${DEPLOYDIR}/uTee-${OPTEE_BIN_EXT}
    fi

    cd ${DEPLOYDIR}
    ln -sf tee.${PLATFORM_FLAVOR}.bin tee.bin
    cd -
}

do_install () {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -m 644 ${B}/core/*.bin ${D}${nonarch_base_libdir}/firmware/

    # Install the TA devkit
    install -d ${D}/usr/include/optee/export-user_ta_${OPTEE_ARCH}/

    for f in ${B}/export-ta_${OPTEE_ARCH}/*; do
        cp -aR $f ${D}/usr/include/optee/export-user_ta_${OPTEE_ARCH}/
    done
}

addtask deploy after do_compile before do_install


FILES_${PN} = "${nonarch_base_libdir}/firmware/"
FILES_${PN}-staticdev = "/usr/include/optee/"
RDEPENDS_${PN}-dev += "${PN}-staticdev"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(imx)"
