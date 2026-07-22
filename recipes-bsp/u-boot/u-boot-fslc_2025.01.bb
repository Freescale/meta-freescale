require recipes-bsp/u-boot/u-boot.inc
require u-boot-fslc-common_${PV}.inc

SUMMARY = "U-Boot bootloader for the FSL Community BSP"
DESCRIPTION = "U-Boot based on mainline U-Boot used by FSL Community BSP in \
               order to provide support for some backported features and fixes, or because it \
               was submitted for revision and it takes some time to become part of a stable \
               version, or because it is not applicable for upstreaming."
SECTION = "bootloaders"

DEPENDS += "bc-native dtc-native gnutls-native python3-setuptools-native"

PROVIDES += "u-boot u-boot-mfgtool"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                HOSTSTRIP=true'

inherit_defer ${@oe.utils.ifelse(d.getVar('UBOOT_PROVIDES_BOOT_CONTAINER') == '1', 'imx-boot-container', '')}
inherit uuu_bootloader_tag

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(imx-generic-bsp)"
