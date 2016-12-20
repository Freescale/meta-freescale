DESCRIPTION = "CEETM TC QDISC"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

SRC_URI = "git://git.freescale.com/ppc/sdk/ceetm.git;branch=sdk-v2.0.x"
SRCREV = "a237e094c3316a55727b855d15480e664545316a"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} SYSROOT=${STAGING_DIR_TARGET}"

do_install(){
    mkdir -p ${D}/${libdir}/tc
    cp ${S}/bin/q_ceetm.so ${D}/${libdir}/tc/
}

FILES_${PN} += "${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(b4|t1|t2|t4|ls1043a|ls1046a)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

