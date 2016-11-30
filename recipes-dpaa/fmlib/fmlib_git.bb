DESCRIPTION = "Frame Manager User Space Library"
SECTION = "fman"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=3f16fa8e677e45af3127c5c4bafc3c00"

PR = "r1"

DEPENDS_virtclass-native = ""

SRC_URI = "git://git.freescale.com/ppc/sdk/fmlib.git;branch=sdk-v2.0.x"
SRCREV = "43fa98fdbf0c697167e415c3f060896d5b482791"

S = "${WORKDIR}/git"

TARGET_ARCH_FMLIB = "${DEFAULTTUNE}"
TARGET_ARCH_FMLIB_e5500 = "ppc32e5500"
TARGET_ARCH_FMLIB_e6500 = "ppc32e6500"
TARGET_ARCH_FMLIB_ls1043ardb = "arm64a53"

EXTRA_OEMAKE = "DESTDIR=${D} PREFIX=${prefix} LIB_DEST_DIR=${libdir} \
        CROSS_COMPILE=${TARGET_PREFIX} KERNEL_SRC=${STAGING_KERNEL_DIR}"

FMLIB_TARGET = "libfm-${TARGET_ARCH_FMLIB}"
FMLIB_TARGET_t1 = "libfm-${TARGET_ARCH_FMLIB}-fmv3l"
do_compile () {
    oe_runmake ${FMLIB_TARGET}.a
}

do_compile_virtclass-native () {
}

do_install () {
    oe_runmake install-${FMLIB_TARGET}
}

do_install_virtclass-native () {
    install -d ${D}/${includedir}
    cp -rf ${S}/include/* ${D}/${includedir}
}

do_compile[depends] += "virtual/kernel:do_shared_workdir"

ALLOW_EMPTY_${PN} = "1"

BBCLASSEXTEND = "native"
COMPATIBLE_HOST_qoriq = ".*"
COMPATIBLE_HOST ?= "(none)"
