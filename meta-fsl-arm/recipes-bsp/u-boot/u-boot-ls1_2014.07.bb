require recipes-bsp/u-boot/u-boot.inc
require u-boot-ls1.inc
inherit fsl-u-boot-localversion

LOCALVERSION ?= "-${SRCBRANCH}"

DEPENDS += "change-file-endianess-native"
PROVIDES += "u-boot"

do_compile_append () {
    if [ "x${UBOOT_CONFIG}" != "x" ]
    then
        for config in ${UBOOT_MACHINE}; do
            case "${config}" in
                *spi*) tclsh ${STAGING_BINDIR_NATIVE}/byte_swap.tcl ${S}/${config}/u-boot.bin ${S}/${config}/u-boot.swap.bin 8
                mv ${S}/${config}/u-boot.swap.bin ${S}/u-boot-${type}.${UBOOT_SUFFIX};;
                *sdcard*)  mv ${S}/${config}/u-boot-with-spl-pbl.bin  ${S}/${config}/u-boot.bin;;
            esac
        done
    fi

}

PACKAGES += "${PN}-images"
FILES_${PN}-images += "/boot"

ALLOW_EMPTY_${PN} = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(ls102xa)"

