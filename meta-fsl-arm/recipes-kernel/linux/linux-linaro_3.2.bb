DESCRIPTION = "Linaor's Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r1"

inherit kernel
COMPATIBLE_MACHINE = "(imx53qsb)"

SRC_URI = "git://git.linaro.org/bsp/freescale/linux-linaro.git;branch=lt-3.2-imx5 \
			file://fix_vpu.patch \
			file://fix_rtc-da9052.patch \
			file://fix_da9052_bl.patch \
			file://fix_div0_ipu.patch \
			file://defconfig \
			"

#lt-3.2-imx6 \
#			file://fix_sata.patch \
#			file://fix_regulator.patch \
#			file://fix_ipuv3.patch \
#			file://fix_usb.patch \
#			file://fix_rtc-da9052.patch \
#			file://fix_da9052_bl.patch


S = "${WORKDIR}/git"
SRCREV = "20401bffc83f4e9fd18fd6032fea22082099ce88"
#"02f4a02c27e1f2f5c79f57394c3f34ab73936d44"

do_compile_prepend() {
	if [ "${@base_contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld/$(CROSS_COMPILE)ld.bfd/g' Makefile
	fi
}

# install nedded headers for imx-test compilation
do_install_append() {
	# bounds.h may be used by a module and is currently missing
	if [ -d include/generated ]; then
		cp include/generated/* $kerneldir/include/generated/
	fi
	# scripts/basic/fixdep is used in imx-test
	mkdir -p $kerneldir/scripts/basic/
	cp scripts/basic/fixdep $kerneldir/scripts/basic/
	# scripts/mod/modpost is used in imx-test
	mkdir -p $kerneldir/scripts/mod
	cp scripts/mod/modpost $kerneldir/scripts/mod
}
