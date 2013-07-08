DESCRIPTION = "Frame Manager User Space Library"
SECTION = "fman"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=3f16fa8e677e45af3127c5c4bafc3c00"

PR = "r1"

DEPENDS = "linux-libc-headers"
DEPENDS_virtclass-native = ""

SRC_URI = "git://git.freescale.com/ppc/sdk/fmlib.git"
SRCREV = "7d843485bdac963d98f81773c0f3cb15936e09da"

S = "${WORKDIR}/git"

TARGET_ARCH_FMLIB = "${DEFAULTTUNE}"
TARGET_ARCH_FMLIB_e5500 = "ppc32e5500"
TARGET_ARCH_FMLIB_e6500 = "ppc32e6500"
COMPATIBLE_HOST_fslmachine = ".*"
COMPATIBLE_HOST ?= "(none)"

EXTRA_OEMAKE = "DESTDIR=${D} PREFIX=${prefix} LIB_DEST_DIR=${libdir} \
        CROSS_COMPILE=${TARGET_PREFIX} KERNEL_SRC=${STAGING_EXECPREFIXDIR}"

do_compile () {
      oe_runmake libfm-${TARGET_ARCH_FMLIB}.a
}

do_compile_virtclass-native () {
}

do_install () {
      oe_runmake install-libfm-${TARGET_ARCH_FMLIB}
}

do_install_virtclass-native () {
      install -d ${D}/${includedir}
      cp -rf ${S}/include/* ${D}/${includedir}
}

ALLOW_EMPTY_${PN} = "1"

BBCLASSEXTEND = "native"
