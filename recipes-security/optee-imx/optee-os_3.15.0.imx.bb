# Copyright (C) 2017-2021 NXP

SUMMARY = "OPTEE OS"
DESCRIPTION = "OPTEE OS"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173"

DEPENDS = "python3-pycryptodomex-native python3-pyelftools-native u-boot-mkimage-native"

SRCBRANCH = "lf-5.10.72_2.2.0"
SRC_URI = "\
	git://source.codeaurora.org/external/imx/imx-optee-os.git;protocol=https;branch=${SRCBRANCH} \
"

SRCREV = "c939619d64dea014ad1b8382356eee4d1cbfbb22"

S = "${WORKDIR}/git"

inherit deploy python3native autotools features_check

REQUIRED_MACHINE_FEATURES = "optee"

# The platform flavor corresponds to the Yocto machine without the leading 'i'.
PLATFORM_FLAVOR                   = "${@d.getVar('MACHINE')[1:]}"
PLATFORM_FLAVOR:imx6qdlsabresd    = "mx6qsabresd"
PLATFORM_FLAVOR:imx6qdlsabreauto  = "mx6qsabreauto"
PLATFORM_FLAVOR:imx6qpdlsolox     = "mx6qsabresd"
PLATFORM_FLAVOR:imx6ul            = "mx6ulevk"
PLATFORM_FLAVOR:imx6ull           = "mx6ullevk"
PLATFORM_FLAVOR:imx6ull           = "mx6ullevk"
PLATFORM_FLAVOR:imx6ulz           = "mx6ulzevk"
PLATFORM_FLAVOR:mx8mq-nxp-bsp             = "mx8mqevk"
PLATFORM_FLAVOR:mx8mm-nxp-bsp             = "mx8mmevk"
PLATFORM_FLAVOR:mx8mn-nxp-bsp             = "mx8mnevk"
PLATFORM_FLAVOR:mx8mp-nxp-bsp             = "mx8mpevk"
PLATFORM_FLAVOR:mx8qm-nxp-bsp             = "mx8qmmek"
PLATFORM_FLAVOR:mx8qxp-nxp-bsp            = "mx8qxpmek"
PLATFORM_FLAVOR:mx8dx-nxp-bsp             = "mx8dxmek"
PLATFORM_FLAVOR:mx8dxl-nxp-bsp            = "mx8dxlevk"

OPTEE_ARCH ?= "arm32"
OPTEE_ARCH:armv7a = "arm32"
OPTEE_ARCH:aarch64 = "arm64"

# Optee-os can be built for 32 bits and 64 bits at the same time
# as long as the compilers are correctly defined.
# For 64bits, CROSS_COMPILE64 must be set
# When defining CROSS_COMPILE and CROSS_COMPILE64, we assure that
# any 32 or 64 bits builds will pass
EXTRA_OEMAKE = " \
	PLATFORM=imx \
	PLATFORM_FLAVOR=${PLATFORM_FLAVOR} \
	CROSS_COMPILE=${HOST_PREFIX} \
	CROSS_COMPILE64=${HOST_PREFIX} \
	CFG_TEE_TA_LOG_LEVEL=0 \
	CFG_TEE_CORE_LOG_LEVEL=0 \
	-C ${S} O=${B}\
"

LDFLAGS = ""
CFLAGS += "--sysroot=${STAGING_DIR_HOST}"
CXXFLAGS += "--sysroot=${STAGING_DIR_HOST}"

do_deploy () {
    install -d ${DEPLOYDIR}
    cp ${B}/core/tee-raw.bin ${DEPLOYDIR}/tee.${PLATFORM_FLAVOR}.bin
    ln -sf tee.${PLATFORM_FLAVOR}.bin ${DEPLOYDIR}/tee.bin

    if [ "${OPTEE_ARCH}" != "arm64" ]; then
        IMX_LOAD_ADDR=`${TARGET_PREFIX}readelf -h ${B}/core/tee.elf | grep "Entry point address" | awk '{print $4}'`
        uboot-mkimage -A arm -O linux -C none -a ${IMX_LOAD_ADDR} -e ${IMX_LOAD_ADDR} \
            -d ${DEPLOYDIR}/tee.${PLATFORM_FLAVOR}.bin ${DEPLOYDIR}/uTee-${OPTEE_BIN_EXT}
    fi
}

do_install () {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -m 644 ${B}/core/*.bin ${D}${nonarch_base_libdir}/firmware/

    # Install the TA devkit
    install -d ${D}${includedir}/optee/export-user_ta_${OPTEE_ARCH}/

    for f in ${B}/export-ta_${OPTEE_ARCH}/*; do
        cp -aR $f ${D}${includedir}/optee/export-user_ta_${OPTEE_ARCH}/
    done

    # Install embedded TAs
    install -d ${D}${nonarch_base_libdir}/optee_armtz
    find ${B}/ta -name '*.ta' | while read name; do
        install -m 444 $name ${D}${nonarch_base_libdir}/optee_armtz/
    done
}

addtask deploy after do_compile before do_install


FILES:${PN} = "${nonarch_base_libdir}/firmware/ ${nonarch_base_libdir}/optee_armtz/"
FILES:${PN}-staticdev = "/usr/include/optee/"
RDEPENDS:${PN}-dev += "${PN}-staticdev"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
