SUMMARY = "DPAA2 Resource Manager Tool"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ec8d84e9cd4de287e290275d09db27f0"

RDEPENDS_${PN} += "bash dtc"

SRC_URI = "git://github.com/nxp-qoriq/qoriq-components/restool;nobranch=1"
SRCREV = "8ddbe4c9559ffad5d7e5dd3cd5f00ceeff7f05d4"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" EXTRA_CFLAGS="-O2 -Wno-missing-field-initializers -Wno-missing-braces"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(qoriq-arm64)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

