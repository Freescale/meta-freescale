DESCRIPTION = "User-Space Data-Path Acceleration Architecture drivers"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"
PR = "r4"

inherit pkgconfig

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "libxml2 libedit ncurses readline flib fmc"
DEPENDS_append_b4860qds = " ipc-ust"
DEPENDS_append_b4420qds = " ipc-ust"

RDEPENDS_${PN} = "libgcc bash"

SRC_URI = "git://git.freescale.com/ppc/sdk/usdpaa.git;nobranch=1"
SRCREV = "d9975948bb6bf9fdcec189c0f1c31ce45f74961c"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${LD}" AR="${AR}"'
export ARCH="${TARGET_ARCH}"

SOC ?= "P4080"
SOC_b4 = "B4860"
SOC_t1 = "T1040"
SOC_t2 = "T2080"
SOC_t4 = "T4240"
SOC_p1023rdb = "P1023"

FMAN_VARIANT ?= "P4080"
FMAN_VARIANT_b4 = "B4860"
FMAN_VARIANT_t1 = "B4860"
FMAN_VARIANT_t2 = "B4860"
FMAN_VARIANT_t4 = "B4860"
FMAN_VARIANT_p1023rdb = "P1023"

do_compile_prepend () {
    export SOC=${SOC}
    export FMC_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/fmc"
    export FMLIB_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/fmd \
        -I ${STAGING_INCDIR}/fmd/Peripherals \
        -I ${STAGING_INCDIR}/fmd/integrations \
        -D${FMAN_VARIANT}"

    export LIBXML2_CFLAGS="$(pkg-config --cflags libxml-2.0)"
    export LIBXML2_LDFLAGS="$(pkg-config --libs --static libxml-2.0)"
    export LIBEDIT_CFLAGS="$(pkg-config --cflags libedit)"
    export LIBEDIT_LDFLAGS="$(pkg-config --libs --static libedit)"
}

do_install () {
    export SOC=${SOC}
    oe_runmake install DESTDIR=${D}
}

PARALLEL_MAKE_pn-${PN} = ""
FILES_${PN} += "/root/SOURCE_THIS /usr/etc/"

COMPATIBLE_HOST_qoriq-ppc = ".*"
COMPATIBLE_HOST ?= "(none)"

