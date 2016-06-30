inherit kernel kernel-arch qoriq_build_64bit_kernel
inherit fsl-kernel-localversion
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Freescale platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;branch=sdk-v1.9.x \
    file://modify-defconfig-t1040-nr-cpus.patch \
    file://net-sctp-CVE-2014-0101.patch \
    file://0001-ARM-8158-LLVMLinux-use-static-inline-in-ARM-ftrace.patch \
    file://0001-ARM-LLVMLinux-Change-extern-inline-to-static-inline.patch \
    file://0003-use-static-inline-in-ARM-lifeboot.h.patch \
    file://0001-powerpc-Align-TOC-to-256-bytes.patch \
    file://fix-the-compile-issue-under-gcc6.patch \
    file://module-remove-MODULE_GENERIC_TABLE.patch \
"
SRCREV = "43cecda943a6c40a833b588801b0929e8bd48813"

S = "${WORKDIR}/git"

DEPENDS_append = " libgcc"
# not put uImage into /boot of rootfs, install kernel-image if needed
RDEPENDS_kernel-base = ""

KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append = " ${TOOLCHAIN_OPTIONS}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"
ZIMAGE_BASE_NAME = "zImage-${PKGE}-${PKGV}-${PKGR}-${MACHINE}-${DATETIME}"
ZIMAGE_BASE_NAME[vardepsexclude] = "DATETIME"

SCMVERSION ?= "y"
LOCALVERSION = ""
DELTA_KERNEL_DEFCONFIG ?= ""

do_configure_prepend() {
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
                ${S}/arch/powerpc/configs/${deltacfg}
        fi
    done
    cp .config ${WORKDIR}/defconfig
}

do_install_append_qoriq-arm() {
    install -m 0644 arch/${ARCH}/boot/zImage ${D}/boot/zImage-${KERNEL_VERSION}
}

do_deploy_append_qoriq-arm() {
    install -m 0644 arch/${ARCH}/boot/zImage ${DEPLOYDIR}/${ZIMAGE_BASE_NAME}.bin
    ln -sf ${ZIMAGE_BASE_NAME}.bin ${DEPLOYDIR}/zImage-${MACHINE}.bin
    ln -sf ${ZIMAGE_BASE_NAME}.bin ${DEPLOYDIR}/zImage
}

FILES_kernel-image += "/boot/zImage*"

# make everything compatible for the time being
COMPATIBLE_MACHINE = "(qoriq)"
