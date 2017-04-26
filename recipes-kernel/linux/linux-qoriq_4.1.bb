inherit kernel qoriq_build_64bit_kernel
inherit fsl-kernel-localversion
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Linux Kernel for Freescale QorIQ platforms"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;nobranch=1 \
    file://modify-defconfig-t1040-nr-cpus.patch \
    file://0003-use-static-inline-in-ARM-lifeboot.h.patch \
    file://fix-the-compile-issue-under-gcc6.patch \
    file://only-set-vmpic_msi_feature-if-CONFIG_EPAPR_PARAVIRT-.patch \
    file://powerpc-fsl-Fix-build-of-the-dtb-embedded-kernel-images.patch \
    file://CVE-2016-2053.patch \
    file://CVE-2016-0758.patch \
    file://powerpc-64e-Convert-cmpi-to-cmpwi-in-head_64.S.patch \
    file://powerpc-vdso64-Use-double-word-compare-on-pointers.patch \
"
SRCREV = "1ae843c08261402b2c35d83422e4fa1e313611f4"

S = "${WORKDIR}/git"

DEPENDS_append = " libgcc"
# not put Images into /boot of rootfs, install kernel-image if needed
RDEPENDS_kernel-base = ""

KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append = " ${TOOLCHAIN_OPTIONS}"
KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

ZIMAGE_BASE_NAME = "zImage-${PKGE}-${PKGV}-${PKGR}-${MACHINE}-${DATETIME}"
ZIMAGE_BASE_NAME[vardepsexclude] = "DATETIME"

SCMVERSION ?= "y"
LOCALVERSION = ""
DELTA_KERNEL_DEFCONFIG ?= ""
DELTA_KERNEL_DEFCONFIG_prepend_qoriq-arm64 = "freescale.config "
DELTA_KERNEL_DEFCONFIG_prepend_fsl-lsch2-32b = "freescale_aarch32.config "

do_merge_delta_config() {
    # copy desired defconfig so we pick it up for the real kernel_do_configure
    cp ${KERNEL_DEFCONFIG} .config
    
    # add config fragments
    for deltacfg in ${DELTA_KERNEL_DEFCONFIG}; do
        if [ -f "${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${deltacfg}
        elif [ -f "${WORKDIR}/${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${WORKDIR}/${deltacfg}
        elif [ -f "${S}/arch/${ARCH}/configs/${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config \
                ${S}/arch/${ARCH}/configs/${deltacfg}
        fi
    done
    cp .config ${WORKDIR}/defconfig
}
addtask merge_delta_config before do_preconfigure after do_patch

# The link of dts folder is needed for 32b compile of aarch64 targets(e.g. ls1043ardb-32b)
do_compile_prepend_fsl-lsch2-32b() {
    ln -sfT ${STAGING_KERNEL_DIR}/arch/arm64/boot/dts/freescale ${STAGING_KERNEL_DIR}/arch/arm/boot/dts/freescale
}

do_install_prepend_fsl-lsch2-32b() {
    rm -f ${STAGING_KERNEL_DIR}/arch/arm/boot/dts/freescale
}

do_install_append_qoriq-arm() {
    install -m 0644 arch/${ARCH}/boot/zImage ${D}/boot/zImage-${KERNEL_VERSION}
    ln -sf zImage-${KERNEL_VERSION} ${D}/boot/zImage
}

do_deploy_append_qoriq-arm() {
    install -m 0644 arch/${ARCH}/boot/zImage ${DEPLOYDIR}/${ZIMAGE_BASE_NAME}.bin
    ln -sf ${ZIMAGE_BASE_NAME}.bin ${DEPLOYDIR}/zImage-${MACHINE}.bin
    ln -sf ${ZIMAGE_BASE_NAME}.bin ${DEPLOYDIR}/zImage
}

FILES_kernel-image += "/boot/zImage*"
COMPATIBLE_MACHINE = "(qoriq)"
