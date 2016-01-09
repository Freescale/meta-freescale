DESCRIPTION = "utility for security boot"
SECTION = "cst"
LICENSE = "BSD"

# TODO: fix license - this file is not a license
LIC_FILES_CHKSUM = "file://include/common.h;beginline=8;endline=30;md5=573e4049ebb103e1cb3f63bc8aaf00b2"

DEPENDS += "openssl"

inherit kernel-arch

SRC_URI = "git://git.freescale.com/ppc/sdk/cst.git;branch=sdk-v1.9.x"
SRCREV = "65f79eb1443211826a58ff225e86b251af5f0e12"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'

PARALLEL_MAKE = ""

do_install () {
    oe_runmake install DESTDIR=${D} BIN_DEST_DIR=${bindir}
}

FILES_${PN}-dbg += "${bindir}/cst/.debug"
BBCLASSEXTEND = "native nativesdk"
