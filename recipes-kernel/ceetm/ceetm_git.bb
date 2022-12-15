DESCRIPTION = "CEETM TC QDISC"
LICENSE = "GPL-2.0-only & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=bac620b9883d38a84dfb73ca7122d915"

SRC_URI = "git://github.com/nxp-qoriq/ceetm;protocol=https;nobranch=1"
SRCREV = "6a7f2ec2091df2f4380cb8d25a36c399aed5af1b"
SRC_URI:append = " file://0001-Makefile-update-CFLAGS.patch \
    file://0001-use-new-api-tc_print_rate.patch \
"
DEPENDS = "iproute2"

S = "${WORKDIR}/git"

export IPROUTE2_DIR="${STAGING_DIR_TARGET}"
WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export CROSS_COMPILE="${WRAP_TARGET_PREFIX}"

LDFLAGS += "${TOOLCHAIN_OPTIONS}"

do_install(){
    mkdir -p ${D}/${libdir}/tc
    cp ${S}/q_ceetm.so ${D}/${libdir}/tc/
}

FILES:${PN} += "${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

