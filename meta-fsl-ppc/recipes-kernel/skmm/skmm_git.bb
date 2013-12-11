DESCRIPTION = "SKMM application for PCIe endpoint"
SECTION = "skmm"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"

DEPENDS = "libedit"

SRCBRANCH = "sdk-v1.4.x"
SRC_URI = "git://git.freescale.com/ppc/sdk/skmm-ep.git;branch=${SRCBRANCH}"
SRCREV = "80d8393a2033b3b0cc8f885702d7b288956f3a37"

COMPATIBLE_MACHINE = "(p4080ds|t4240qds)"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'MACHINE=${MACHINE}'

export LIBEDIT_CFLAGS="$(pkg-config --cflags libedit)"
export LIBEDIT_LDFLAGS="$(pkg-config --libs --static libedit)"

do_compile () {
	export ARCH=${TARGET_ARCH}
	oe_runmake HOST=x86_64 clean
	oe_runmake HOST=x86_64
	oe_runmake HOST=powerpc clean
	oe_runmake HOST=powerpc
}

do_install () {
	oe_runmake ARCH=${TARGET_ARCH} HOST=x86_64 install DESTDIR=${D}
	oe_runmake ARCH=${TARGET_ARCH} HOST=powerpc install DESTDIR=${D}
}

FILES_${PN} += "/home/root/.skmm/*"
