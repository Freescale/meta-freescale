# Copyright (C) 2012 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

INC_PR = "r30"

inherit kernel

# Put a local version until we have a true SRCREV to point to
LOCALVERSION ?= "+yocto-${DATE}"

SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git \
           file://defconfig \
"

S = "${WORKDIR}/git"

kernel_conf_variable() {
	CONF_SED_SCRIPT="$CONF_SED_SCRIPT /CONFIG_$1[ =]/d;"
	if test "$2" = "n"
	then
		echo "# CONFIG_$1 is not set" >> ${S}/.config
	else
		echo "CONFIG_$1=$2" >> ${S}/.config
	fi
}

do_configure_prepend() {
	echo "" > ${S}/.config
	CONF_SED_SCRIPT=""

	kernel_conf_variable LOCALVERSION "\"${LOCALVERSION}\""
	kernel_conf_variable LOCALVERSION_AUTO n

	sed -e "${CONF_SED_SCRIPT}" < '${WORKDIR}/defconfig' >> '${S}/.config'
}

# install nedded headers for imx-test compilation
do_install_append() {
	# bounds.h may be used by a module and is currently missing
	if [ -d include/generated ]; then
		cp include/generated/* $kerneldir/include/generated/
	fi

	# Host architecture object file
	rm -f $kerneldir/scripts/kconfig/kxgettext.o
}

sysroot_stage_all_append() {
	# denzil does not have KERNEL_SRC_PATH so we default to /kernel
	destdir=${KERNEL_SRC_PATH}
	if [ -z "$destdir" ]; then
		destdir=/kernel
	fi

	# Copy native binaries need for imx-test build onto sysroot
	mkdir -p ${SYSROOT_DESTDIR}/usr/src/kernel/scripts/basic \
	         ${SYSROOT_DESTDIR}/usr/src/kernel/scripts/mod
	cp ${S}/scripts/basic/fixdep ${SYSROOT_DESTDIR}$destdir/scripts/basic
	cp ${S}/scripts/mod/modpost ${SYSROOT_DESTDIR}$destdir/scripts/mod
}
