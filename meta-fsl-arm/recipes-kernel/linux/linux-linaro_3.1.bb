DESCRIPTION = "Linaor's Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r1"

inherit kernel
COMPATIBLE_MACHINE = "(imx53qsb)"

SRC_URI = "git://git.linaro.org/bsp/freescale/linux-linaro.git;tag=lt-3.1-2011.12 \
			file://defconfig \
			"

S = "${WORKDIR}/git"
SRCREV = "a54d3b98256254af0d1d9144916f6ec7c908fe85"

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
