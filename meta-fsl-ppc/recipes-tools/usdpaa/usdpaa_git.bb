DESCRIPTION = "User-Space Data-Path Acceleration Architecture drivers"
SECTION = "usdpaa"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"
PR = "r1"

inherit pkgconfig

DEPENDS = "libxml2 libedit ncurses readline flib"
RDEPENDS_${PN} = "libgcc"

SRC_URI = "git://git.freescale.com/ppc/sdk/usdpaa.git"
SRCREV = "2e21af59ea1eddd938404d29ee0864e6cfe936a8"
SRCREV_e6500 = "53c621f783c9d10d46828854120a3ebd58389779"
SRCREV_e6500-64b = "53c621f783c9d10d46828854120a3ebd58389779"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'V=1 CC="${CC}" LD="${LD}" AR="${AR}"'

do_compile_prepend () {
	export ARCH=${TARGET_ARCH}
	export LIBXML2_CFLAGS="$(pkg-config --cflags libxml-2.0)"
	export LIBXML2_LDFLAGS="$(pkg-config --libs --static libxml-2.0)"
	export LIBEDIT_CFLAGS="$(pkg-config --cflags libedit)"
	export LIBEDIT_LDFLAGS="$(pkg-config --libs --static libedit)"
}

do_install () {
	oe_runmake ARCH=${TARGET_ARCH} install DESTDIR=${D}
}

PARALLEL_MAKE_pn-${PN} = ""

FILES_${PN} += "/root/SOURCE_THIS /usr/etc/"
RDEPENDS_${PN} += "bash"
