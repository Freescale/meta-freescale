require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
COMPATIBLE_MACHINE = "(imx28evk|mx5|mx6)"

DEPENDS_mxs += "elftosb-native"

SRCREV = "b6c3efe8a8ea1b77c85c232d86a080d1c5ed2b23"

PV = "v2012.04.01"
PR = "r2"

SRC_URI = "git://github.com/Freescale/u-boot-imx.git"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_compile_prepend() {
	if [ "${@base_contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld/$(CROSS_COMPILE)ld.bfd/g' config.mk
	fi
}
