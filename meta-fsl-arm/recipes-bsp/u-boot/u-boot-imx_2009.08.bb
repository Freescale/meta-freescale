# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"

DEPENDS_mxs += "elftosb-native"

PR = "r15"

# Revision of imx_2.6.35_10.12.01 branch
SRCREV_mxs = "e4437f1c192a1a68028e6fcff3f50ff50352041d"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV_mx5 = "897922d01c812be802e4a928b937535ea1b8e076"
SRC_URI_append_imx5 = " \
           file://mx53_loco_bootenv.patch \
"

# Revision of imx_3.0.35_1.1.0 branch
SRCREV_mx6 = "98a5299c945cb7e440e3c3d9c572f017e5a02ede"
SRC_URI_append_mx6 = " \
   file://mx6q_sabreauto-Fix-the-patch-for-the-default-environ.patch \
   file://mx6q_sabresd-Change-default-environment-to-work-with.patch \
"

# Revision of imx_3.0.35_12.10.02 branch
SRCREV_mx6sl = "27cdce50f9a7fc30d7cff067b420a32e5f2c5959"
SRC_URI_append_mx6sl = " \
   file://mx6sl_evk-Fix-the-patch-for-the-default-environment-.patch \
"

# Revision of imx_3.0.35_3.0.0 branch
SRCREV_mx6dl = "d4057eba315edbc8335c138c0c9edbe2109ea36e"
SRC_URI_append_mx6dl = " \
   file://mx6dl_sabresd-Change-default-environment-to-work-wit.patch \
"

SRC_URI = "git://git.freescale.com/imx/uboot-imx.git"

UBOOT_MACHINE_imx53qsb = "mx53_loco_config"
UBOOT_MACHINE_imx53ard = "mx53_ard_ddr3_config"
UBOOT_MACHINE_imx51evk = "mx51_bbg_config"
UBOOT_MACHINE_imx6qsabrelite = "mx6q_sabrelite_config"
UBOOT_MACHINE_imx6qsabreauto = "mx6q_sabreauto_config"
UBOOT_MACHINE_imx6qsabresd = "mx6q_sabresd_config"
UBOOT_MACHINE_imx6dlsabresd = "mx6dl_sabresd_config"
UBOOT_MACHINE_imx6slevk = "mx6sl_evk_config"
UBOOT_MACHINE_imx28evk = "mx28_evk_config"

UBOOT_MAKE_TARGET = "u-boot.bin"

# Please, add the following variables to conf/local.conf
# in order to use this u-boot version
# UBOOT_SUFFIX = "bin"
# UBOOT_PADDING = "2"
# PREFERRED_PROVIDER_u-boot = "u-boot-imx"

S = "${WORKDIR}/git"
EXTRA_OEMAKE += 'HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_compile_prepend() {
	if [ "${@base_contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld/$(CROSS_COMPILE)ld.bfd/g' config.mk
	fi
}

COMPATIBLE_MACHINE = "(imx28evk|mx5|mx6)"
