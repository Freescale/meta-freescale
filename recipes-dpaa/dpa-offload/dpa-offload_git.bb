DESCRIPTION = "Data-Path Acceleration Architecture Offloading User-Space Drivers"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=c03ebb7a330b209986517466c74da72c"

inherit pkgconfig

DEPENDS = "usdpaa fmlib"
DEPENDS_append_b4860qds = " ipc-ust"
DEPENDS_append_b4420qds = " ipc-ust"

RDEPENDS_${PN} = "libgcc bash"
RDEPENDS_${PN}_append_b4860qds = " ipc-ust"
RDEPENDS_${PN}_append_b4420qds = " ipc-ust"

SRC_URI = "git://git.freescale.com/ppc/sdk/usdpaa/dpa-offload.git;branch=sdk-v2.0.x"
SRCREV = "68faa6a64745151fcfef73c9ddc612d9728c2f07"

S = "${WORKDIR}/git"

WRAP_ARCH ?= "${TARGET_ARCH}"
WRAP_ARCH_fsl-lsch2 = "arm64"
EXTRA_OEMAKE = 'CC="${CC}" LD="${LD}" ARCH="${WRAP_ARCH}"'

FMAN_VARIANT ?= "P4080"
FMAN_VARIANT_b4 = "FMAN_V3H"
FMAN_VARIANT_t1 = "FMAN_V3L"
FMAN_VARIANT_t2 = "FMAN_V3H"
FMAN_VARIANT_t4 = "FMAN_V3H"
FMAN_VARIANT_p1023rdb = "P1023"

do_compile_prepend () {
    export USDPAA_CFLAGS="-I ${STAGING_INCDIR}/usdpaa"
    export FMLIB_CFLAGS="-I ${STAGING_INCDIR}/fmd \
        -I ${STAGING_INCDIR}/fmd/Peripherals \
        -I ${STAGING_INCDIR}/fmd/integrations \
        -D${FMAN_VARIANT} -DNCSW_LINUX"
}

do_install () {
    oe_runmake install DESTDIR=${D}
}

ALLOW_EMPTY_${PN} = "1"
PARALLEL_MAKE_pn-${PN} = ""

COMPATIBLE_MACHINE = "(qoriq-ppc|fsl-lsch2)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

