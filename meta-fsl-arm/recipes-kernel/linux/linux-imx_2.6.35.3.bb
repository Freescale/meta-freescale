# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r30"

inherit kernel
COMPATIBLE_MACHINE = "(mxs|mx5)"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV = "3e2396eddb362ff70ee6eac43fb1f27f217dc0d1"

SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git \
     	   file://0002-cgroupfs-create-sys-fs-cgroup-to-mount-cgroupfs-on.patch \
           file://egalax_ts-enable-single-event-support.patch \
           file://NFS-Fix-nfsroot-support.patch \
           file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
           file://no-unaligned-access.patch \
           file://mx28-removecpufreq.patch \
           file://mxs-duart-use-ttyAMA-for-device-name.patch \
           file://w1-fix-support-multiple-master.patch \
           file://smsc95xx-randomize-mac-once.patch \
           file://dm9601-support-for-usb-ethernet-0x0fe6-0x9700.patch \
           file://wire-up-sys_accept4-on-ARM.patch \
           file://0001-mx53_loco-add-USR-led-support.patch \
           file://002_Return_ERESTARTSYS_from_IPU_GET_EVENT.patch \
           file://003_Sanitise_ipu_interrupt_return_value.patch \
           file://defconfig \
          "

#EXTRA_OEMAKE += "V=1"

S = "${WORKDIR}/git"

# install nedded headers for imx-test compilation
do_install_append() {
	# bounds.h may be used by a module and is currently missing
	if [ -d include/generated ]; then
		cp include/generated/* $kerneldir/include/generated/
	fi
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
