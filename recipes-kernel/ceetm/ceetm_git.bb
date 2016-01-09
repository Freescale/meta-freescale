DESCRIPTION = "CEETM TC QDISC"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

inherit module qoriq_build_64bit_kernel

SRC_URI = "git://git.freescale.com/ppc/sdk/ceetm.git;branch=sdk-v1.9.x"
SRCREV = "2c79d0b3465368a19bb2b4ccd680ddd297ebe377"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} SYSROOT=${STAGING_DIR_TARGET}"
export KERNEL_PATH = "${STAGING_KERNEL_DIR}"

do_configure[depends] += "virtual/kernel:do_shared_workdir"
do_install(){
	mkdir -p ${D}/usr/driver/ceetm
	mkdir -p ${D}/${libdir}/tc
	cp ${S}/bin/ceetm.ko ${D}/usr/driver/ceetm
	cp ${S}/bin/q_ceetm.so ${D}/${libdir}/tc/.
}

FILES_${PN} += "/usr/driver/ceetm ${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(e6500-64b|t1040|t1042)"
