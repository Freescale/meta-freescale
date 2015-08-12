require recipes-bsp/u-boot/u-boot.inc
inherit fsl-u-boot-localversion

DESCRIPTION = "U-boot provided by Freescale with focus on QorIQ boards"
HOMEPAGE = "http://u-boot.sf.net"
SECTION = "bootloaders"
PROVIDES = "virtual/bootloader u-boot"
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
DEPENDS = "libgcc ${@base_contains('TCMODE', 'external-fsl', '', 'virtual/${TARGET_PREFIX}gcc', d)}"
DEPENDS_append_qoriq-arm = " change-file-endianess-native dtc-native tcl-native"
DEPENDS_append_qoriq-ppc = " boot-format-native"

inherit deploy

SRCBRANCH = "sdk-v1.8.x"
SRC_URI = "git://git.freescale.com/ppc/sdk/u-boot.git;branch=${SRCBRANCH} \
    file://0001-u-boot-mpc85xx-u-boot-.lds-remove-_GLOBAL_OFFSET_TAB.patch \
    file://gcc5.patch \
    file://add-fgnu89-inline-option-for-gcc5.patch \
"
SRCREV = "6ba8eedbcdc4b063f59a63e6288b938af739e8ad"

python () {
    if d.getVar("TCMODE", True) == "external-fsl":
        return

    ml = d.getVar("MULTILIB_VARIANTS", True)
    arch = d.getVar("OVERRIDES", True)

    if "e5500-64b:" in arch or "e6500-64b:" in arch:
        if not "lib32" in ml:
            raise bb.parse.SkipPackage("Building the u-boot for this arch requires multilib to be enabled")
        sys_multilib = 'powerpc' + d.getVar('TARGET_VENDOR') + 'mllib32-' + d.getVar('HOST_OS')
        d.setVar('DEPENDS_append', ' lib32-gcc-cross-powerpc lib32-libgcc')
        d.setVar('PATH_append', ':' + d.getVar('STAGING_BINDIR_NATIVE') + '/' + sys_multilib)
        d.setVar('TOOLCHAIN_OPTIONS_append', '/../lib32-' + d.getVar("MACHINE"))
        d.setVar("WRAP_TARGET_PREFIX", sys_multilib + '-')
}

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LOCALVERSION = "${@d.getVar('SDK_VERSION', True).partition(' ')[0]}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CROSS_COMPILE=${WRAP_TARGET_PREFIX} CC="${WRAP_TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

do_compile_append_qoriq-ppc() {
    python ./tools/genboardscfg.py

    for board in ${UBOOT_MACHINE}; do
        if ! grep -wq $board ${S}/boards.cfg;then
            echo "WARNING: $board not supported in boards.cfg"
            continue
        fi

        case "${board}" in
            *SDCARD*)   UBOOT_TARGET="u-boot-sd";;
            *SPIFLASH*) UBOOT_TARGET="u-boot-spi";;
            *NAND*)     UBOOT_TARGET="u-boot-nand";;
            *SRIO*)     UBOOT_TARGET="u-boot-srio";;
            *)      UBOOT_TARGET="";;
        esac

        # deal with sd/spi/nand/srio image
        UBOOT_SOURCE=u-boot.bin
        if [ "x${UBOOT_TARGET}" != "x" ] && echo $board |egrep -qi "SECBOOT|SECURE"; then
            cp ${S}/${board}/${UBOOT_SOURCE}  ${S}/${board}/${UBOOT_TARGET}.bin
        elif [ "x${UBOOT_TARGET}" != "x" ]; then
            # some boards' final binary was not named as u-boot.bin
            if [ "${UBOOT_TARGET}" = "u-boot-nand" ];then
                if echo $board |egrep -q "^(BSC|C29|P10|P2020RDB)";then
                    UBOOT_SOURCE=u-boot-with-spl.bin
                elif echo $board |egrep -q "^(B4|T1|T2|T4)";then
                    UBOOT_SOURCE=u-boot-with-spl-pbl.bin
                elif echo $board |egrep -q "^(P2041|P3|P4|P5)";then
                    UBOOT_SOURCE=u-boot.pbl
                fi
            elif [ "${UBOOT_TARGET}" = "u-boot-spi" ];then
                if echo $board |egrep -q "^(P10|P2020RDB)";then
                    UBOOT_SOURCE=u-boot-with-spl.bin
                elif echo $board |egrep -q "^(T1|T2)";then
                    UBOOT_SOURCE=u-boot-with-spl-pbl.bin
                elif echo $board |egrep -q "^(B4|P2041|P3|P4|P5|T4)";then
                    UBOOT_SOURCE=u-boot.pbl
                fi
            elif [ "${UBOOT_TARGET}" = "u-boot-sd" ];then
                if echo $board |egrep -q "^(P10|P2020RDB)";then
                    UBOOT_SOURCE=u-boot-with-spl.bin
                elif echo $board |egrep -q "^(B4|T1|T2|T4)";then
                    UBOOT_SOURCE=u-boot-with-spl-pbl.bin
                elif echo $board |egrep -q "^(P2041|P3|P4|P5)";then
                    UBOOT_SOURCE=u-boot.pbl
                fi
            fi
            cp ${S}/${board}/${UBOOT_SOURCE}  ${S}/${board}/${UBOOT_TARGET}.bin

            # use boot-format to regenerate spi image if BOOTFORMAT_CONFIG is not empty
            if [ "${UBOOT_TARGET}" = "u-boot-spi" ] && [ -n "${BOOTFORMAT_CONFIG}" ];then
                ${STAGING_BINDIR_NATIVE}/boot_format \
                ${STAGING_DATADIR_NATIVE}/boot_format/${BOOTFORMAT_CONFIG} \
                ${S}/${board}/${UBOOT_SOURCE} -spi ${S}/${board}/${UBOOT_TARGET}.bin
            fi
        fi
    done
}

do_compile_append_qoriq-arm () {
    if [ "x${UBOOT_CONFIG}" != "x" ]
    then
        for config in ${UBOOT_MACHINE}; do
            case "${config}" in
                *spi*) tclsh ${STAGING_BINDIR_NATIVE}/byte_swap.tcl ${S}/${config}/u-boot-dtb.bin ${S}/${config}/u-boot.swap.bin 8
                mv ${S}/${config}/u-boot.swap.bin ${S}/u-boot-${type}.${UBOOT_SUFFIX};;
                *nand* | *sdcard*) mv ${S}/${config}/u-boot-with-spl-pbl.bin ${S}/${config}/u-boot.bin;;
            esac
        done
    fi
}

do_install_append_qoriq-ppc() {
    for board in ${UBOOT_MACHINE}; do
        if ! grep -wq $board ${S}/boards.cfg;then
            continue
        fi

        case "${board}" in
            *SDCARD*)   UBOOT_TARGET="u-boot-sd";;
            *SPIFLASH*) UBOOT_TARGET="u-boot-spi";;
            *NAND*)     UBOOT_TARGET="u-boot-nand";;
            *SRIO*)     UBOOT_TARGET="u-boot-srio";;
            *)      UBOOT_TARGET="u-boot";;
        esac

        if [ -f ${S}/${board}/${UBOOT_TARGET}.bin ]; then
            mkdir -p ${D}/boot/
            install ${S}/${board}/${UBOOT_TARGET}.bin ${D}/boot/${UBOOT_TARGET}-${board}-${PV}-${PR}.bin
            ln -sf ${UBOOT_TARGET}-${board}-${PV}-${PR}.bin ${D}/boot/${UBOOT_TARGET}.bin
        fi
    done
}

do_deploy_append_qoriq-ppc() {
    if [ "x${UBOOT_MACHINES}" = "x" ]; then
        UBOOT_MACHINES=${UBOOT_MACHINE}
    fi

    for board in ${UBOOT_MACHINES}; do
        if ! grep -wq $board ${S}/boards.cfg;then
            continue
        fi

        case "${board}" in
            *SDCARD*)   UBOOT_TARGET="u-boot-sd";;
            *SPIFLASH*) UBOOT_TARGET="u-boot-spi";;
            *NAND*)     UBOOT_TARGET="u-boot-nand";;
            *SRIO*)     UBOOT_TARGET="u-boot-srio";;
            *)      UBOOT_TARGET="u-boot";;
        esac

        if [ -f ${S}/${board}/${UBOOT_TARGET}.bin ]; then
            mkdir -p ${DEPLOYDIR}
            install ${S}/${board}/${UBOOT_TARGET}.bin ${DEPLOYDIR}/${UBOOT_TARGET}-${board}-${PV}-${PR}.bin

            cd ${DEPLOYDIR}
            rm -f ${UBOOT_TARGET}-${board}.bin
            ln -sf ${UBOOT_TARGET}-${board}-${PV}-${PR}.bin ${UBOOT_TARGET}-${board}.bin
        fi
    done
}
addtask deploy after do_install

PACKAGES += "${PN}-images"
FILES_${PN}-images += "/boot"

COMPATIBLE_MACHINE = "(qoriq)"
