DESCRIPTION = "Frame Manager Configuration tool"
SECTION = "fmc"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=a504ab5a8ff235e67c7301214749346c"

PR = "r1"

SRC_URI = "git://git.freescale.com/ppc/sdk/fmc.git"
SRCREV = "6db53fae9dfe72db0312a383733e0d3598aad3f2"

DEPENDS = "libxml2 fmlib tclap"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '
EXTRA_OEMAKE_virtclass-native = 'FMCHOSTMODE=1 FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '

do_compile () {
    if [ "b4860qds" = "${MACHINE}" ] || [ "b4420qds" = "${MACHINE}" ];then
        EXTRA_OEMAKE_PLATFORM="b4860qds"
    elif [ "t4240qds" = "${MACHINE}" ] || [ "t4160qds" = "${MACHINE}" ];then
        EXTRA_OEMAKE_PLATFORM="t4240qds"
    elif [ "p1023rds" = "${MACHINE}" ];then
        EXTRA_OEMAKE_PLATFORM="p1023rds"
    else
        EXTRA_OEMAKE_PLATFORM=""
    fi
    oe_runmake MACHINE=${EXTRA_OEMAKE_PLATFORM} -C source
}

do_install () {
    install -d ${D}/${bindir}
    install -m 755 ${S}/source/fmc ${D}/${bindir}/fmc

    install -d ${D}/etc/fmc/config
    install -m 644 ${S}/etc/fmc/config/hxs_pdl_v3.xml ${D}/etc/fmc/config
}

PARALLEL_MAKE = ""

BBCLASSEXTEND = "native"
