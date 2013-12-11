DESCRIPTION = "Frame Manager Configuration tool"
SECTION = "fmc"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=a504ab5a8ff235e67c7301214749346c"

PR = "r2"

SRCBRANCH = "sdk-v1.4.x"
SRC_URI = "git://git.freescale.com/ppc/sdk/fmc.git;branch=${SRCBRANCH}"
SRCREV = "f2e1a831a96f1d04d3d5a5970d2e54c38098cf39"

DEPENDS = "libxml2 fmlib tclap"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '
EXTRA_OEMAKE_virtclass-native = 'FMCHOSTMODE=1 FMD_USPACE_HEADER_PATH="${STAGING_INCDIR}/fmd" \
    FMD_USPACE_LIB_PATH="${STAGING_LIBDIR}" LIBXML2_HEADER_PATH="${STAGING_INCDIR}/libxml2" \
    TCLAP_HEADER_PATH="${STAGING_INCDIR}" '

PARALLEL_MAKE = ""

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

    install -d ${D}/${includedir}/fmc
    install ${S}/source/fmc.h ${D}/${includedir}/fmc

    install -d ${D}/${libdir}
    install ${S}/source/libfmc.a ${D}/${libdir}
}

BBCLASSEXTEND = "native"
