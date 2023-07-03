DESCRIPTION = "Frame Manager User Space Library"
SECTION = "fman"
LICENSE = "BSD-3-Clause & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9c7bd5e45d066db084bdb3543d55b1ac"

PR = "r1"

SRC_URI = "git://github.com/nxp-qoriq/fmlib;protocol=https;nobranch=1"
SRCREV = "9fb916f255214013aa6003496c47bab0b5add2d9"

S = "${WORKDIR}/git"

CFLAGS += "-fmacro-prefix-map=${STAGING_KERNEL_DIR}=/usr/src/debug/fmlib/git-r1 \
		-fdebug-prefix-map=${STAGING_KERNEL_DIR}=/usr/src/debug/fmlib/git-r1"

EXTRA_OEMAKE = "DESTDIR=${D} PREFIX=${prefix} LIB_DEST_DIR=${libdir} \
        CROSS_COMPILE=${TARGET_PREFIX} KERNEL_SRC=${STAGING_KERNEL_DIR}"

TARGET_ARCH_FMLIB = "${DEFAULTTUNE}"
TARGET_ARCH_FMLIB:qoriq-arm = "arm"
TARGET_ARCH_FMLIB:qoriq-arm64 = "arm"
TARGET_ARCH_FMLIB:e5500 = "ppc32e5500"
TARGET_ARCH_FMLIB:e6500 = "ppc32e6500"
TARGET_ARCH_FMLIB:e500mc = "ppce500mc"
TARGET_ARCH_FMLIB:e500v2 = "ppce500v2"

FMLIB_TARGET = "libfm-${TARGET_ARCH_FMLIB}"
FMLIB_TARGET:t1 = "libfm-${TARGET_ARCH_FMLIB}-fmv3l"

do_compile () {
    oe_runmake ${FMLIB_TARGET}.a
}

do_install () {
    oe_runmake install-${FMLIB_TARGET}
}

do_compile[depends] += "virtual/kernel:do_shared_workdir"

ALLOW_EMPTY:${PN} = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE ?= "(qoriq)"
