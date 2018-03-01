inherit kernel qoriq_build_64bit_kernel
inherit fsl-kernel-localversion

SUMMARY = "Linux Kernel for Freescale QorIQ platforms"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/linux;nobranch=1 \
"
SRCREV = "b226445f42e0acc83db095a267bc003fcc56653b"

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
DELTA_KERNEL_DEFCONFIG_prepend_qoriq-arm64 = "lsdk.config "
DELTA_KERNEL_DEFCONFIG_prepend_fsl-lsch2-32b = "multi_v7_lpae.config multi_v8.config lsdk.config "
DELTA_KERNEL_DEFCONFIG_prepend_ls102xa = "multi_v7_lpae.config lsdk.config "

do_merge_delta_config[dirs] = "${B}" 

do_merge_delta_config() {
    # create .config with make config
    oe_runmake  -C ${S} O=${B} ${KERNEL_DEFCONFIG}

    # add config fragments
    for deltacfg in ${DELTA_KERNEL_DEFCONFIG}; do
        if [ -f ${S}/arch/${ARCH}/configs/${deltacfg} ]; then
            oe_runmake  -C ${S} O=${B} ${deltacfg}
        elif [ -f "${WORKDIR}/${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${WORKDIR}/${deltacfg}
        elif [ -f "${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${deltacfg}
        fi
    done
    cp .config ${WORKDIR}/defconfig
}
addtask merge_delta_config before do_preconfigure after do_patch

# The link of dts folder is needed for 32b compile of aarch64 targets(e.g. ls1043ardb-32b)
do_compile_prepend_fsl-lsch2-32b() {
    ln -sfT ${STAGING_KERNEL_DIR}/arch/arm64/boot/dts/freescale ${STAGING_KERNEL_DIR}/arch/arm/boot/dts/freescale
}

do_install_append_qoriq-arm() {
    install -m 0644 arch/${ARCH}/boot/zImage ${D}/boot/zImage-${KERNEL_VERSION}
    ln -sf zImage-${KERNEL_VERSION} ${D}/boot/zImage
}

FILES_kernel-image += "/boot/zImage*"
COMPATIBLE_MACHINE = "(qoriq)"
