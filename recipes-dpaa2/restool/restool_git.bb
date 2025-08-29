SUMMARY = "DPAA2 Resource Manager Tool"
LICENSE = "BSD-3-Clause | GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=83af78c71766dd5fb1c1c3dd64a75ee7"

SRC_URI = "git://github.com/nxp-qoriq/restool;protocol=https;nobranch=1"
SRCREV = "8f73f2600111440d034227966d6ff03ec47d8f1a"

inherit bash-completion

EXTRA_OEMAKE = 'CC="${CC}" MANPAGE= EXTRA_CFLAGS="-O2 -Wno-missing-field-initializers -Wno-missing-braces -Wno-maybe-uninitialized -Wno-date-time"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(qoriq-arm64)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} += "bash dtc"
RDEPENDS:${PN}-bash-completion += "bash"

