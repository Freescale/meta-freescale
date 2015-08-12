DESCRIPTION = "CEETM TC QDISC"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

inherit module qoriq_build_64bit_kernel

SRC_URI = "git://git.freescale.com/ppc/sdk/ceetm.git;nobranch=1"
SRCREV =  "ecf55c9ca0cd42a212653e1f99c19cd611e3a008"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} SYSROOT=${STAGING_DIR_TARGET}"
export KERNEL_PATH = "${STAGING_KERNEL_DIR}"

do_configure[depends] += "virtual/kernel:do_shared_workdir"
do_configure_prepend () {
    sed -i 's,$(KERNEL_PATH)/.config,$(KBUILD_OUTPUT)/.config,' ${S}/ceetm_module/Makefile
}

do_install(){
	mkdir -p ${D}/usr/driver/ceetm
	mkdir -p ${D}/${libdir}/tc
	cp ${S}/bin/ceetm.ko ${D}/usr/driver/ceetm
	cp ${S}/bin/q_ceetm.so ${D}/${libdir}/tc/.
}

FILES_${PN} += "/usr/driver/ceetm ${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(e6500-64b|t1040rdb|t1040rdb-64b|t1042rdb|t1042rdb-64b)"
