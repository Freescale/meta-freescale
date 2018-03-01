SUMMARY = "Frame Manager Configuration tool"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=a504ab5a8ff235e67c7301214749346c"

PR = "r2"

DEPENDS = "libxml2 fmlib tclap"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/fmc;nobranch=1"
SRCREV = "8c9f127138dc803bf58bef75e9fc092dc60f9c53"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '
EXTRA_OEMAKE_virtclass-native = 'FMCHOSTMODE=1 FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '

EXTRA_OEMAKE_PLATFORM ?= ""
EXTRA_OEMAKE_PLATFORM_ls1043a = "ls1043"
EXTRA_OEMAKE_PLATFORM_ls1046a = "ls1046"
EXTRA_OEMAKE_PLATFORM_ls1088a = "ls1088"

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

