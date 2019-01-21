DESCRIPTION = "CEETM TC QDISC"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=bac620b9883d38a84dfb73ca7122d915"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/ceetm;nobranch=1"
SRCREV = "6a7f2ec2091df2f4380cb8d25a36c399aed5af1b"

DEPENDS = "iproute2"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}" IPROUTE2_DIR="{STAGING_DIR_TARGET}"'

do_compile_prepend () {
    cp ${RECIPE_SYSROOT}/usr/include/include/json_print.h ${RECIPE_SYSROOT}/usr/include
}

do_install(){
    mkdir -p ${D}/${libdir}/tc
    cp ${S}/q_ceetm.so ${D}/${libdir}/tc/
}

FILES_${PN} += "${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

