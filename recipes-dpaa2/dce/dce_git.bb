DESCRIPTION = "Decompression Compression Engine Userspace Utils"
SECTION = "dpaa2"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3bb80dec5c1b94d99a1422cbfd96192c"

SRC_URI = "git://git.freescale.com/ppc/sdk/dce.git;nobranch=1 \
           file://Makefile-obey-LDFLAGS.patch \
"
SRCREV = "c31f81667f5cebb3e5331c50655473301672e4de"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(ls2080a|ls2088a)"
