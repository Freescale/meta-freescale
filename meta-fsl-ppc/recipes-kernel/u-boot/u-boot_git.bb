DESCRIPTION = "U-boot bootloader"
HOMEPAGE = "http://u-boot.sf.net"
SECTION = "bootloaders"
PROVIDES = "virtual/bootloader"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

PR = "r7"
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "boot-format-native virtual/${TARGET_PREFIX}gcc"

inherit deploy

SRCREV = "348d8fceba39e5622d315ffda7d72ff6bbcf05ec"
SRC_URI = "git://git.freescale.com/ppc/sdk/u-boot.git \
		"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'
EXTRA_OEMAKE_e5500-64b = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS} -m32" LD="${LD} -melf32ppc"'

PACKAGE_ARCH = "${MACHINE_ARCH}"

USRC ?= ""
S = ${@base_conditional("USRC", "", "${WORKDIR}/git", "${USRC}", d)}

do_compile () {
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	if [ "x${UBOOT_MACHINES}" == "x" ]; then
		UBOOT_MACHINES=${UBOOT_MACHINE}
	fi

	for board in ${UBOOT_MACHINES}; do
		oe_runmake O=${board} distclean
		oe_runmake O=${board} ${board}
		oe_runmake O=${board} all

		case "${board}" in
		*SDCARD*)	UBOOT_TARGET="u-bootx-sd";;
		*SPIFLASH*)	UBOOT_TARGET="u-bootx-spi";;
		*)		UBOOT_TARGET="";;
		esac

		if [ "x${UBOOT_TARGET}" != "x" ]; then
			${STAGING_BINDIR_NATIVE}/boot_format \
				${STAGING_DATADIR_NATIVE}/${BOOTFORMAT_CONFIG} \
				${S}/${board}/u-boot.bin -spi ${S}/${board}/${UBOOT_TARGET}.bin
		fi
	done
}

do_install(){
	if [ "x${UBOOT_MACHINES}" == "x" ]; then
		UBOOT_MACHINES=${UBOOT_MACHINE}
	fi

	for board in ${UBOOT_MACHINES}; do
		case "${board}" in
		*SDCARD*)	UBOOT_TARGET="u-bootx-sd";;
		*SPIFLASH*)	UBOOT_TARGET="u-bootx-spi";;
		*NAND*)		UBOOT_TARGET="u-boot-nand";;
		*)		UBOOT_TARGET="u-boot";;
		esac

		if [ -f ${S}/${UBOOT_TARGET}.bin ]; then
			install ${S}/${board}/${UBOOT_TARGET}.bin ${D}/boot/${UBOOT_TARGET}-${MACHINE}-${PV}-${PR}.bin
			ln -sf ${UBOOT_TARGET}-${MACHINE}-${PV}-${PR}.bin ${D}/boot/${UBOOT_TARGET}.bin
		fi
	done
}

do_deploy(){
	if [ "x${UBOOT_MACHINES}" == "x" ]; then
		UBOOT_MACHINES=${UBOOT_MACHINE}
	fi

	for board in ${UBOOT_MACHINES}; do
		case "${board}" in
		*SDCARD*)	UBOOT_TARGET="u-bootx-sd";;
		*SPIFLASH*)	UBOOT_TARGET="u-bootx-spi";;
		*NAND*)		UBOOT_TARGET="u-boot-nand";;
		*)		UBOOT_TARGET="u-boot";;
		esac

	        if [ -f ${S}/${board}/${UBOOT_TARGET}.bin ]; then
			mkdir -p ${DEPLOY_DIR_IMAGE}
	        	install ${S}/${board}/${UBOOT_TARGET}.bin ${DEPLOY_DIR_IMAGE}/${UBOOT_TARGET}-${MACHINE}-${PV}-${PR}.bin

			cd ${DEPLOY_DIR_IMAGE}
			rm -f ${UBOOT_TARGET}-${MACHINE}.bin
			ln -sf ${UBOOT_TARGET}-${MACHINE}-${PV}-${PR}.bin ${UBOOT_TARGET}-${MACHINE}.bin
		fi
	done
}
addtask deploy after do_install

FILES_${PN} += "/boot"

ALLOW_EMPTY = 1 
