DESCRIPTION = "Frame Manager User Space Library"
SECTION = "fman"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=9c7bd5e45d066db084bdb3543d55b1ac"

PR = "r1"

SRC_URI = "git://github.com/nxp-qoriq/fmlib;nobranch=1"
SRCREV = "69a70474cd8411d5a099c34f40760b6567d781d6"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "DESTDIR=${D} PREFIX=${prefix} LIB_DEST_DIR=${libdir} \
        CROSS_COMPILE=${TARGET_PREFIX} KERNEL_SRC=${STAGING_KERNEL_DIR}"

TARGET_ARCH_FMLIB = "${DEFAULTTUNE}"
TARGET_ARCH_FMLIB_qoriq-arm = "arm"
TARGET_ARCH_FMLIB_qoriq-arm64 = "arm"
TARGET_ARCH_FMLIB_e5500 = "ppc32e5500"
TARGET_ARCH_FMLIB_e6500 = "ppc32e6500"
TARGET_ARCH_FMLIB_e500mc = "ppce500mc"
TARGET_ARCH_FMLIB_e500v2 = "ppce500v2"

FMLIB_TARGET = "libfm-${TARGET_ARCH_FMLIB}"
FMLIB_TARGET_t1 = "libfm-${TARGET_ARCH_FMLIB}-fmv3l"

do_compile () {
    oe_runmake ${FMLIB_TARGET}.a
}

do_install () {
    oe_runmake install-${FMLIB_TARGET}
}

do_compile[depends] += "virtual/kernel:do_shared_workdir"

ALLOW_EMPTY_${PN} = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE ?= "(qoriq)"
