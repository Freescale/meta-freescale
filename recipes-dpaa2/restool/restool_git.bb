SUMMARY = "DPAA2 Resource Manager Tool"
LICENSE = "BSD-3-Clause | GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=83af78c71766dd5fb1c1c3dd64a75ee7"

SRC_URI = "git://github.com/nxp-qoriq/restool;protocol=https;nobranch=1 \
    file://disable-manpage-generation.patch \
"
SRCREV = "8c45e04363bf2a984a87b24d1dac80a286dd40d8"

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

