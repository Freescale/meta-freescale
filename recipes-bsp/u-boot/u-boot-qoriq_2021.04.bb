require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-Boot provided by Freescale with focus on QorIQ boards"
PROVIDES += "u-boot"

inherit fsl-u-boot-localversion

LICENSE = "GPL-2.0-only & BSD-3-Clause & BSD-2-Clause & LGPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = " \
    file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://Licenses/bsd-2-clause.txt;md5=6a31f076f5773aabd8ff86191ad6fdd5 \
    file://Licenses/bsd-3-clause.txt;md5=4a1190eac56a9db675d58ebe86eaf50c \
    file://Licenses/lgpl-2.0.txt;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
    file://Licenses/lgpl-2.1.txt;md5=4fbd65380cdd255951079008b364516c \
"

SRC_URI = "git://github.com/nxp-qoriq/u-boot;protocol=https;nobranch=1"
SRCREV= "1c0116f3da250c5a52858c53efb8b38c0963f477"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
PV:append = "+fslgit"
LOCALVERSION = "+fsl"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "libgcc virtual/${TARGET_PREFIX}gcc bison-native bc-native swig-native python3-native"
DEPENDS:append:qoriq-arm64 = " dtc-native"
DEPENDS:append:qoriq-arm = " dtc-native"
DEPENDS:append:qoriq-ppc = " boot-format-native"

python () {
    if d.getVar("TCMODE") == "external-fsl":
        return

    ml = d.getVar("MULTILIB_VARIANTS")
    arch = d.getVar("OVERRIDES")

    if "e5500-64b:" in arch or "e6500-64b:" in arch:
        if not "lib32" in ml:
            raise bb.parse.SkipPackage("Building the u-boot for this arch requires multilib to be enabled")
        sys_multilib = d.getVar('TARGET_VENDOR') + 'mllib32-linux'
        sys_original = d.getVar('TARGET_VENDOR') + '-' + d.getVar('TARGET_OS')
        workdir = d.getVar('WORKDIR')
        d.setVar('DEPENDS:append', ' lib32-gcc-cross-powerpc lib32-libgcc')
        d.setVar('PATH:append', ':' + d.getVar('STAGING_BINDIR_NATIVE') + '/powerpc' + sys_multilib)
        d.setVar('TOOLCHAIN_OPTIONS', '--sysroot=' + workdir + '/lib32-recipe-sysroot')
        d.setVar("WRAP_TARGET_PREFIX", 'powerpc' + sys_multilib + '-')
    elif "fsl-lsch2-32b:" in arch:
        if not "lib64" in ml:
            raise bb.parse.SkipRecipe("Building the u-boot for this arch requires multilib to be enabled")
        sys_multilib = d.getVar('TARGET_VENDOR') + 'mllib64-linux'
        sys_original = d.getVar('TARGET_VENDOR') + '-' + d.getVar('TARGET_OS')
        workdir = d.getVar('WORKDIR')
        d.setVar('DEPENDS:append', ' lib64-gcc-cross-aarch64 lib64-libgcc')
        d.setVar('PATH:append', ':' + d.getVar('STAGING_BINDIR_NATIVE') + '/aarch64' + sys_multilib)
        d.setVar('TOOLCHAIN_OPTIONS', '--sysroot=' + workdir + '/lib64-recipe-sysroot')
        d.setVar("WRAP_TARGET_PREFIX", 'aarch64' + sys_multilib + '-')
}

LE_UBOOT_FOR_ARMBE_TARGET ?= "0"
ENDIANNESS_GCC = "${@bb.utils.contains("LE_UBOOT_FOR_ARMBE_TARGET", "1", "-mlittle-endian", "", d)}"
ENDIANNESS_LD = "${@bb.utils.contains("LE_UBOOT_FOR_ARMBE_TARGET", "1", "-EL", "", d)}"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
EXTRA_OEMAKE = 'CROSS_COMPILE=${WRAP_TARGET_PREFIX} CC="${WRAP_TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS} ${ENDIANNESS_GCC}" LD="${WRAP_TARGET_PREFIX}ld ${ENDIANNESS_LD}" V=1'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE += 'STAGING_INCDIR=${STAGING_INCDIR_NATIVE} STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'

do_compile:append:qoriq() {
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
FILES:${PN}-images += "/boot"
COMPATIBLE_MACHINE = "(qoriq)"
