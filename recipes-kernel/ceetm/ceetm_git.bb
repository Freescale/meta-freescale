SUMMARY = "CEETM traffic-control qdisc"
DESCRIPTION = "Traffic-control qdisc for the QorIQ DPAA CEETM hardware scheduler."
HOMEPAGE = "https://github.com/nxp-qoriq/ceetm"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "iproute2"

SRC_URI = "git://github.com/nxp-qoriq/ceetm;protocol=https;nobranch=1 \
           file://0001-Makefile-update-CFLAGS.patch \
           file://0001-Makefile-Fix-build-error-with-gcc15-YOCIMX-8305.patch \
"
SRCREV = "46b3565a48ca20f90ad601cef8250cdd35f18b22"

export IPROUTE2_DIR = "${STAGING_DIR_TARGET}"
WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export CROSS_COMPILE = "${WRAP_TARGET_PREFIX}"

LDFLAGS += "${TOOLCHAIN_OPTIONS}"

do_install(){
    install -d ${D}${libdir}/tc
    install -m 0755 ${S}/q_ceetm.so ${D}${libdir}/tc/
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
FILES:${PN} += "${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(qoriq)"
