DESCRIPTION = "SKMM application for PCIe endpoint"
SECTION = "skmm-ep"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"

DEPENDS = "libedit openssl virtual/kernel"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.freescale.com/ppc/sdk/skmm-ep.git;branch=sdk-v2.0.x \
    file://add-two-missing-header-files.patch \
"
SRCREV = "de6816c7d66129683bc62229b482ac3cf585d896"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'ARCH=${TARGET_ARCH} MACHINE=${MACHINE} EXTRA_LDFLAGS="${LDFLAGS}"'

export LIBEDIT_CFLAGS = "`pkg-config --cflags libedit`"
export LIBEDIT_LDFLAGS = "`pkg-config --libs --static libedit`"

do_compile () {
	oe_runmake
}

do_install () {
	oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(p4080ds|t4240qds|c293pcie)"
