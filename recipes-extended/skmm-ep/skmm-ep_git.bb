DESCRIPTION = "SKMM application for PCIe endpoint"
SECTION = "skmm-ep"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"

DEPENDS = "libedit openssl virtual/kernel"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.freescale.com/ppc/sdk/skmm-ep.git;nobranch=1 \
    file://add-two-missing-header-files.patch \
"
SRCREV = "27156a6621c8f6d7f98210b1ca5cd97bde926875"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'MACHINE=${MACHINE}'

export LIBEDIT_CFLAGS = "`pkg-config --cflags libedit`"
export LIBEDIT_LDFLAGS = "`pkg-config --libs --static libedit`"

do_compile () {
	export ARCH=${TARGET_ARCH}
	oe_runmake
}

do_install () {
	oe_runmake ARCH=${TARGET_ARCH} install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(p4080ds|t4240qds|c293pcie)"
