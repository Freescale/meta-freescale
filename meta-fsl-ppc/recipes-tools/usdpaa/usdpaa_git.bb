DESCRIPTION = "User-Space Data-Path Acceleration Architecture drivers"
SECTION = "usdpaa"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"
PR = "r4"

inherit pkgconfig

DEPENDS = "libxml2 libedit ncurses readline flib fmc"
RDEPENDS_${PN} = "libgcc"

SRC_URI = "git://git.freescale.com/ppc/sdk/usdpaa.git"
SRCREV = "670ad8827fd83169ccb54a008c638dbb497b1c61"

COMPATIBLE_HOST_fslmachine = ".*"
COMPATIBLE_HOST ?= "(none)"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'V=1 CC="${CC}" LD="${LD}" AR="${AR}"'
export ARCH="${TARGET_ARCH}"

do_compile_prepend () {
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
RDEPENDS_${PN} += "bash"
