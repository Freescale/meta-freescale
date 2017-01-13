require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-Boot provided by Freescale with focus on QorIQ boards"
PROVIDES += "u-boot"
LICENSE = "GPLv2 & BSD-3-Clause & BSD-2-Clause & LGPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = " \
    file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://Licenses/bsd-2-clause.txt;md5=6a31f076f5773aabd8ff86191ad6fdd5 \
    file://Licenses/bsd-3-clause.txt;md5=4a1190eac56a9db675d58ebe86eaf50c \
    file://Licenses/lgpl-2.0.txt;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
    file://Licenses/lgpl-2.1.txt;md5=4fbd65380cdd255951079008b364516c \
"

PV_append = "+fslgit"
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "libgcc virtual/${TARGET_PREFIX}gcc"
DEPENDS_append_qoriq-arm64 = " change-file-endianess-native dtc-native tcl-native"
DEPENDS_append_qoriq-arm = " change-file-endianess-native dtc-native tcl-native"
DEPENDS_append_qoriq-ppc = " boot-format-native"

SRC_URI = "git://git.freescale.com/ppc/sdk/u-boot.git;nobranch=1 \
    file://fix-build-error-under-gcc6.patch \
"
SRCREV = "199df35d12857a68d5c8ee999dbdd167c3888d62"

S = "${WORKDIR}/git"

python () {
    if d.getVar("TCMODE", True) == "external-fsl":
        return

    ml = d.getVar("MULTILIB_VARIANTS", True)
    arch = d.getVar("OVERRIDES", True)

    if "e5500-64b:" in arch or "e6500-64b:" in arch:
        if not "lib32" in ml:
            raise bb.parse.SkipPackage("Building the u-boot for this arch requires multilib to be enabled")
        sys_multilib = 'powerpc' + d.getVar('TARGET_VENDOR', False) + 'mllib32-' + d.getVar('HOST_OS', False)
        d.setVar('DEPENDS_append', ' lib32-gcc-cross-powerpc lib32-libgcc')
        d.setVar('PATH_append', ':' + d.getVar('STAGING_BINDIR_NATIVE', False) + '/' + sys_multilib)
        d.setVar('TOOLCHAIN_OPTIONS_append', '/../lib32-' + d.getVar("MACHINE", False))
        d.setVar("WRAP_TARGET_PREFIX", sys_multilib + '-')
    elif "fsl-lsch2-32b:" in arch:
        if not "lib64" in ml:
            raise bb.parse.SkipPackage("Building the u-boot for this arch requires multilib to be enabled")
        sys_multilib = 'aarch64' + d.getVar('TARGET_VENDOR', False) + 'mllib64-linux'
        d.setVar('DEPENDS_append', ' lib64-gcc-cross-aarch64 lib64-libgcc')
        d.setVar('PATH_append', ':' + d.getVar('STAGING_BINDIR_NATIVE', False) + '/' + sys_multilib)
        d.setVar('TOOLCHAIN_OPTIONS_append', '/../lib64-' + d.getVar("MACHINE", False))
        d.setVar("WRAP_TARGET_PREFIX", sys_multilib + '-')
}

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
EXTRA_OEMAKE = 'CROSS_COMPILE=${WRAP_TARGET_PREFIX} CC="${WRAP_TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" V=1'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'

inherit fsl-u-boot-localversion
LOCALVERSION = "+fsl"

do_compile_append_qoriq() {
    unset i j k
    for config in ${UBOOT_MACHINE}; do
        i=`expr $i + 1`;
        for type in ${UBOOT_CONFIG}; do
            j=`expr $j + 1`;
            for binary in ${UBOOT_BINARIES}; do
                k=`expr $k + 1`
                if [ $j -eq $i ] && [ $k -eq $i ]; then
                    if [ -n "${BOOTFORMAT_CONFIG}" ] && echo "${type}" |grep -q spi;then
                        # regenerate spi binary if BOOTFORMAT_CONFIG is set
                        boot_format ${STAGING_DATADIR_NATIVE}/boot_format/${BOOTFORMAT_CONFIG} \
                            ${config}/u-boot-${type}.${UBOOT_SUFFIX} -spi ${config}/u-boot.format.bin
                        cp ${config}/u-boot.format.bin ${config}/u-boot-${type}.${UBOOT_SUFFIX}
                    elif [ "qspi" = "${type}" ];then
                        cp ${config}/${binary} ${config}/u-boot-${type}-${PV}-${PR}.${UBOOT_SUFFIX}
                    fi
                fi
            done
            unset k
        done
        unset j
    done
    unset i
}


PACKAGES += "${PN}-images"
FILES_${PN}-images += "/boot"
COMPATIBLE_MACHINE = "(qoriq)"
