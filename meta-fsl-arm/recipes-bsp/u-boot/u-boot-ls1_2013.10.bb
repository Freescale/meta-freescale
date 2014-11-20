require recipes-bsp/u-boot/u-boot.inc
require u-boot-ls1.inc
inherit fsl-u-boot-localversion

LOCALVERSION ?= "-SDK-V1.3"

DEPENDS += "u-boot-ls1-tools-native"
PROVIDES += "u-boot"

do_compile_append () {
    case "${UBOOT_MACHINE}" in
        *spi*) tclsh byte_swap.tcl ${S}/u-boot.bin ${S}/u-boot.swap.bin 8 
        mv ${S}/u-boot.swap.bin ${S}/u-boot.bin;;
    esac
}

PACKAGES += "${PN}-images"
FILES_${PN}-images += "/boot"

ALLOW_EMPTY_${PN} = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(ls102xa)"

