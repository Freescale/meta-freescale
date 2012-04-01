require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
COMPATIBLE_MACHINE = "(imx53qsb|imx53ard|imx51evk|imx28evk|imx6qsabrelite)"

DEPENDS_mxs += "elftosb-native"

PR = "r3"

SRCREV = "6751b05f855bbe56005d5b88d4eb58bcd52170d2"

PV = "v2011.12+git"

SRC_URI = "git://git.denx.de/u-boot.git;branch=master;protocol=git \
           file://mx53-fix-uart-clock.patch \
           file://mx6qsabrelite_add_CONFIG_REVISION_TAG.patch \
           file://mx6qsabrelite_add_MACH_TYPE_MX6Q_SABRELITE.patch \
	   file://mx6qsabrelite-fix-console.patch \	
	  "

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_compile_prepend() {
	if [ "${@base_contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld/$(CROSS_COMPILE)ld.bfd/g' config.mk
	fi
}
