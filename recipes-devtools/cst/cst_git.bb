SUMMARY = "utility for security boot"
SECTION = "cst"
LICENSE = "BSD"

# TODO: fix license - this file is not a license
LIC_FILES_CHKSUM = "file://common/include/global.h;endline=26;md5=e31e31c35cc53f6fba7e3c4e9baf4339"

DEPENDS += "openssl"

inherit kernel-arch

SRC_URI = "git://git.freescale.com/ppc/sdk/cst.git;branch=sdk-v2.0.x"
SRCREV = "d1c6c28ee78f8ed2aa92dbd6c0c00e6defe1242b"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'

PARALLEL_MAKE = ""

do_install () {
    oe_runmake install DESTDIR=${D} BIN_DEST_DIR=${bindir}
}

FILES_${PN}-dbg += "${bindir}/cst/.debug"
BBCLASSEXTEND = "native nativesdk"
