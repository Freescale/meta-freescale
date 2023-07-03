SUMMARY = "Frame Manager Configuration tool"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a504ab5a8ff235e67c7301214749346c"

PR = "r2"

DEPENDS = "libxml2 fmlib tclap"

SRC_URI = "git://github.com/nxp-qoriq/fmc;protocol=https;nobranch=1"
SRCREV = "63c8ac99899a9bcd723801579b4d786594670455"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '
EXTRA_OEMAKE:class-native = 'FMCHOSTMODE=1 FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '

EXTRA_OEMAKE_PLATFORM ?= ""
EXTRA_OEMAKE_PLATFORM:ls1043a = "ls1043"
EXTRA_OEMAKE_PLATFORM:ls1046a = "ls1046"
EXTRA_OEMAKE_PLATFORM:ls1088a = "ls1088"
EXTRA_OEMAKE_PLATFORM:p1020 = "p4080ds"
EXTRA_OEMAKE_PLATFORM:p2020 = "p4080ds"
EXTRA_OEMAKE_PLATFORM:p2041 = "p4080ds"
EXTRA_OEMAKE_PLATFORM:p3041 = "p4080ds"
EXTRA_OEMAKE_PLATFORM:p4080 = "p4080ds"
EXTRA_OEMAKE_PLATFORM:p5040 = "p4080ds"


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
