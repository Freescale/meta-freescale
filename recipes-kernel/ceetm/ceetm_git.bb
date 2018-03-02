DESCRIPTION = "CEETM TC QDISC"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=dde0931a586dd5b30076639e29ba3a7b"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/ceetm;nobranch=1"
SRCREV = "f58304d7e2185003d0f4ff604bc8e4316d463a03"

DEPENDS = "iproute2"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}" IPROUTE2_DIR="{STAGING_DIR_TARGET}"'

do_install(){
    mkdir -p ${D}/${libdir}/tc
    cp ${S}/q_ceetm.so ${D}/${libdir}/tc/
}

FILES_${PN} += "${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(b4|t1|t2|t4|ls1043a|ls1046a|ls2088a|ls1088a)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

