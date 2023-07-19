SUMMARY = "DPAA2 Resource Manager Tool"
LICENSE = "BSD-3-Clause | GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ec8d84e9cd4de287e290275d09db27f0"

SRC_URI = "git://github.com/nxp-qoriq/restool;protocol=https;nobranch=1 \
    file://disable-manpage-generation.patch \
"
SRCREV = "46604e41f4c7e54efa62503c6b4629321b21e056"

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

