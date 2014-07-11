DESCRIPTION = "Frame Manager User Space Library"
SECTION = "fman"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=3f16fa8e677e45af3127c5c4bafc3c00"

PR = "r1"

DEPENDS += "virtual/kernel"
DEPENDS_virtclass-native = ""

SRC_URI = "git://git.freescale.com/ppc/sdk/fmlib.git;nobranch=1"
SRCREV = "6efc100cf470f271a312860c06717928554e3492"

S = "${WORKDIR}/git"

TARGET_ARCH_FMLIB = "${DEFAULTTUNE}"
TARGET_ARCH_FMLIB_e5500 = "ppc32e5500"
TARGET_ARCH_FMLIB_e6500 = "ppc32e6500"
COMPATIBLE_HOST_qoriq-ppc = ".*"
COMPATIBLE_HOST ?= "(none)"

EXTRA_OEMAKE = "DESTDIR=${D} PREFIX=${prefix} LIB_DEST_DIR=${libdir} \
        CROSS_COMPILE=${TARGET_PREFIX} KERNEL_SRC=${STAGING_KERNEL_DIR}"

do_compile () {
    if [ "t1040" = "${SOC_FAMILY}" -o "t1042" = "${SOC_FAMILY}" ];then
        oe_runmake libfm-${TARGET_ARCH_FMLIB}-fmv3.a
    else
        oe_runmake libfm-${TARGET_ARCH_FMLIB}.a
    fi
}

do_compile_virtclass-native () {
}

do_install () {
    if [ "t1040" = "${SOC_FAMILY}" -o "t1042" = "${SOC_FAMILY}" ];then
        oe_runmake install-libfm-${TARGET_ARCH_FMLIB}-fmv3
    else
        oe_runmake install-libfm-${TARGET_ARCH_FMLIB}
    fi
}

do_install_virtclass-native () {
    install -d ${D}/${includedir}
    cp -rf ${S}/include/* ${D}/${includedir}
}

ALLOW_EMPTY_${PN} = "1"

BBCLASSEXTEND = "native"
