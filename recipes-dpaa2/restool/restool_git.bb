SUMMARY = "DPAA2 Resource Manager Tool"
LICENSE = "BSD-3-Clause | GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=ec8d84e9cd4de287e290275d09db27f0"

SRC_URI = "git://github.com/nxp-qoriq/restool;protocol=https;nobranch=1 \
    file://disable-manpage-generation.patch \
"
SRCREV = "65c9a10d95b19e61d2f86dbcaa45d12ba98e85e1"

S = "${WORKDIR}/git"

inherit bash-completion

EXTRA_OEMAKE = 'CC="${CC}" MANPAGE= EXTRA_CFLAGS="-O2 -Wno-missing-field-initializers -Wno-missing-braces -Wno-maybe-uninitialized -Wno-date-time"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(qoriq-arm64)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} += "bash dtc"
RDEPENDS:${PN}-bash-completion += "bash"

