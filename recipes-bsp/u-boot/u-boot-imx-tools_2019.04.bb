require recipes-bsp/u-boot/u-boot-tools.inc
require u-boot-imx-common.inc

PROVIDES_append_class-target = " ${MLPREFIX}u-boot-tools"
PROVIDES_append_class-native = " u-boot-tools-native"
PROVIDES_append_class-nativesdk = " nativesdk-u-boot-tools"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE_class-target = "(mx6|mx7|mx8)"
