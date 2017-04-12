SUMMARY = "DPAA2 Resource Manager Tool"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=e613b54e73c0e4a2a8918c61924bd623"

RDEPENDS_${PN} += "bash"

SRC_URI = "git://git.freescale.com/ppc/sdk/restool.git;nobranch=1"
SRCREV = "eba25cdfdccef45f7ba02dd2f829b9b55198fbb1"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" EXTRA_CFLAGS="-Wno-missing-field-initializers -Wno-missing-braces"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(ls2080ardb|ls2088ardb)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

