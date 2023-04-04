SUMMARY = "Frame Manager Configuration tool"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=a504ab5a8ff235e67c7301214749346c"

PR = "r2"

DEPENDS = "libxml2 fmlib tclap"

SRC_URI = "git://github.com/nxp-qoriq/qoriq-components/fmc;nobranch=1"
SRCREV = "c2ed7c269e86ac6a0aac361f5876c96e700443f4"

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
EXTRA_OEMAKE_PLATFORM_p1020 = "p4080ds"
EXTRA_OEMAKE_PLATFORM_p2020 = "p4080ds"
EXTRA_OEMAKE_PLATFORM_p2041 = "p4080ds"
EXTRA_OEMAKE_PLATFORM_p3041 = "p4080ds"
EXTRA_OEMAKE_PLATFORM_p4080 = "p4080ds"
EXTRA_OEMAKE_PLATFORM_p5040 = "p4080ds"


do_compile () {
    oe_runmake MACHINE=${EXTRA_OEMAKE_PLATFORM} -C source
}

do_install () {
    install -d ${D}/${bindir}
    install -m 755 ${S}/source/fmc ${D}/${bindir}

    install -d ${D}${sysconfdir}/fmc/config
    install -m 644 ${S}${sysconfdir}/fmc/config/* ${D}${sysconfdir}/fmc/config

    install -d ${D}/${includedir}/fmc
    install ${S}/source/fmc.h ${D}/${includedir}/fmc

    install -d ${D}/${libdir}
    install ${S}/source/libfmc.a ${D}/${libdir}
}

PARALLEL_MAKE = ""

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(qoriq)"
