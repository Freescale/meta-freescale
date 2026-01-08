inherit kernel qoriq_build_64bit_kernel siteinfo
inherit fsl-kernel-localversion kernel-yocto

SUMMARY = "Linux Kernel for NXP QorIQ platforms"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

DEPENDS:append = " libgcc coreutils-native"
# not put Images into /boot of rootfs, install kernel-image if needed
RDEPENDS:${KERNEL_PACKAGE_NAME}-base = ""

KERNEL_CC:append = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD:append = " ${TOOLCHAIN_OPTIONS}"
KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

ZIMAGE_BASE_NAME = "zImage-${PKGE}-${PKGV}-${PKGR}-${MACHINE}-${DATETIME}"
ZIMAGE_BASE_NAME[vardepsexclude] = "DATETIME"

# Set the PV to the correct kernel version to satisfy the kernel version sanity check
PV = "${LINUX_VERSION}+git${SRCPV}"

SCMVERSION ?= "y"
LOCALVERSION = ""

KBUILD_DEFCONFIG = "${KERNEL_DEFCONFIG}"
EXTRA_KBUILD_DEFCONFIG:qoriq-arm64 = "lsdk.config"
EXTRA_KBUILD_DEFCONFIG:qoriq-arm   = "multi_v7_lpae.config lsdk.config"

do_configure() {
    # create config with make config
    oe_runmake  -C ${S} O=${B} ${KERNEL_DEFCONFIG}

    # check if bigendian is enabled
    if [ "${SITEINFO_ENDIANNESS}" = "be" ]; then
        echo "CONFIG_CPU_BIG_ENDIAN=y" >> .config
        echo "CONFIG_MTD_CFI_BE_BYTE_SWAP=y" >> .config
    fi
    for deltacfg in ${EXTRA_KBUILD_DEFCONFIG}; do
        if [ -f ${S}/arch/${ARCH}/configs/${deltacfg} ]; then
            oe_runmake  -C ${S} O=${B} ${deltacfg}
        elif [ -f "${S}/${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${S}/${deltacfg}
        elif [ -f "${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${deltacfg}
        fi
    done
    cp .config ${UNPACKDIR}/defconfig
}

FILES:${KERNEL_PACKAGE_NAME}-image += "/boot/zImage*"
INSANE_SKIP:${PN}-src += " buildpaths"
COMPATIBLE_MACHINE = "(qoriq)"
