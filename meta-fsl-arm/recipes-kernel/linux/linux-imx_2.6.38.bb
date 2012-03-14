# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r1"

DEFAULT_PREFERENCE_imx53qsb = "-1"
DEFAULT_PREFERENCE_imx53ard = "-1"

inherit kernel
COMPATIBLE_MACHINE = "(imx53qsb|imx53ard)"

SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git;tag=rel_imx_2.6.38_12.01.01 \
           file://egalax_ts-enable-single-event-support.patch \
           file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
           file://defconfig \
          "

S = "${WORKDIR}/git"

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
