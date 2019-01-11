SUMMARY = "DPAA2 Resource Manager Tool"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ec8d84e9cd4de287e290275d09db27f0"

RDEPENDS_${PN} += "bash dtc"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/restool;nobranch=1 \
    file://0001-restool-fix-build-error-with-gcc7.patch"

SRCREV = "2b4dd211d5c584d5f81cfa530a3a93854dc1d490"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" EXTRA_CFLAGS="-Wno-missing-field-initializers -Wno-missing-braces"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(ls2080ardb|ls2088ardb|ls1088ardb|ls1043ardb|ls1046ardb)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

