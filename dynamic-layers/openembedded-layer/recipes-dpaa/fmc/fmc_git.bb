SUMMARY = "Frame Manager Configuration tool"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=a504ab5a8ff235e67c7301214749346c"

PR = "r2"

DEPENDS = "libxml2 fmlib tclap"

SRC_URI = "git://git.freescale.com/ppc/sdk/fmc.git;branch=sdk-v2.0.x"
SRCREV = "81e4796fd22e32fe7ab77f075c1879d7bb75c02e"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '
EXTRA_OEMAKE_virtclass-native = 'FMCHOSTMODE=1 FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '

EXTRA_OEMAKE_PLATFORM ?= ""
EXTRA_OEMAKE_PLATFORM_b4 = "b4860qds"
EXTRA_OEMAKE_PLATFORM_t2 = "b4860qds"
EXTRA_OEMAKE_PLATFORM_t4 = "b4860qds"
EXTRA_OEMAKE_PLATFORM_t1 = "t1040qds"
EXTRA_OEMAKE_PLATFORM_ls1043a = "ls1043"
EXTRA_OEMAKE_PLATFORM_ls1046a = "ls1046"

do_compile () {
    oe_runmake MACHINE=${EXTRA_OEMAKE_PLATFORM} -C source
}

do_install () {
    install -d ${D}/${bindir}
    install -m 755 ${S}/source/fmc ${D}/${bindir}

    install -d ${D}${sysconfdir}/fmc/config
    install -m 644 ${S}${sysconfdir}/fmc/config/hxs_pdl_v3.xml ${D}${sysconfdir}/fmc/config

    install -d ${D}/${includedir}/fmc
    install ${S}/source/fmc.h ${D}/${includedir}/fmc

    install -d ${D}/${libdir}
    install ${S}/source/libfmc.a ${D}/${libdir}
}

PARALLEL_MAKE = ""

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

