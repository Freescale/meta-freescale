DESCRIPTION = "User-Space Data-Path Acceleration Architecture drivers"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"
PR = "r4"

inherit pkgconfig

DEPENDS = "libxml2 libedit ncurses readline flib fmc"
RDEPENDS_${PN} = "libgcc bash"

SRC_URI = "git://git.freescale.com/ppc/sdk/usdpaa.git"
SRCREV = "97fe45d9697ef339e10a1885539b23fa7fcb113e"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${LD}" AR="${AR}"'
export ARCH="${TARGET_ARCH}"

do_compile_prepend () {
	case ${MACHINE} in
		b4420qds|b4420qds-64b|b4860qds|b4860qds-64b) SOC=B4860;;
		t4240qds|t4240qds-64b) SOC=T4240;;
		p1023rdb) SOC=P1023;;
		*) SOC=P4080;;
	esac
	export FMC_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/fmc"
	export FMLIB_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/fmd \
		-I ${STAGING_INCDIR}/fmd/Peripherals \
		-I ${STAGING_INCDIR}/fmd/integrations \
		-D $SOC"

	export LIBXML2_CFLAGS="$(pkg-config --cflags libxml-2.0)"
	export LIBXML2_LDFLAGS="$(pkg-config --libs --static libxml-2.0)"
	export LIBEDIT_CFLAGS="$(pkg-config --cflags libedit)"
	export LIBEDIT_LDFLAGS="$(pkg-config --libs --static libedit)"
}

do_install () {
	oe_runmake install DESTDIR=${D}
}

PARALLEL_MAKE_pn-${PN} = ""
FILES_${PN} += "/root/SOURCE_THIS /usr/etc/"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_HOST_fslmachine = ".*"
COMPATIBLE_HOST ?= "(none)"

