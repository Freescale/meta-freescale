DESCRIPTION = "SKMM application for PCIe endpoint"
SECTION = "skmm-ep"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"

DEPENDS = "libedit openssl virtual/kernel"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.freescale.com/ppc/sdk/skmm-ep.git;branch=sdk-v2.0.x \
    file://add-two-missing-header-files.patch \
    file://skmm-ep-fix-the-inline-function-definition-with-gcc-.patch \
    file://skmm-ep-remove-duplicated-pa_to_va-and-va_to_pa.patch \
"
SRCREV = "fc43230ac3044917c9df12e9e4f6a38a4d9b80aa"

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
