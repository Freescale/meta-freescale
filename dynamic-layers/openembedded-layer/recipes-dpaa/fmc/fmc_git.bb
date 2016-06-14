DESCRIPTION = "Frame Manager Configuration tool"
SECTION = "fmc"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=a504ab5a8ff235e67c7301214749346c"

PR = "r2"

SRC_URI = "git://git.freescale.com/ppc/sdk/fmc.git;branch=sdk-v1.9.x"
SRCREV = "a079d2c844edd85dff85a317a63198e7988bcd09"

DEPENDS = "libxml2 fmlib tclap"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_HOST_qoriq-ppc = ".*"
COMPATIBLE_HOST ?= "(none)"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '
EXTRA_OEMAKE_virtclass-native = 'FMCHOSTMODE=1 FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '

PARALLEL_MAKE = ""

EXTRA_OEMAKE_PLATFORM ?= ""
EXTRA_OEMAKE_PLATFORM_b4 = "b4860qds"
EXTRA_OEMAKE_PLATFORM_t2 = "b4860qds"
EXTRA_OEMAKE_PLATFORM_t4 = "b4860qds"
EXTRA_OEMAKE_PLATFORM_t1 = "t1040qds"

do_compile () {
    oe_runmake MACHINE=${EXTRA_OEMAKE_PLATFORM} -C source
}

do_install () {
    install -d ${D}/${bindir}
    install -m 755 ${S}/source/fmc ${D}/${bindir}/fmc

    install -d ${D}/etc/fmc/config
    install -m 644 ${S}/etc/fmc/config/hxs_pdl_v3.xml ${D}/etc/fmc/config

    install -d ${D}/${includedir}/fmc
    install ${S}/source/fmc.h ${D}/${includedir}/fmc

    install -d ${D}/${libdir}
    install ${S}/source/libfmc.a ${D}/${libdir}
}

BBCLASSEXTEND = "native"
