SUMMARY = "DPAA2 Resource Manager Tool"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ec8d84e9cd4de287e290275d09db27f0"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/restool;nobranch=1 \
    file://disable-manpage-generation.patch \
"
SRCREV = "d29522aef9f92ff2557978d5d3979b771a9576fe"

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

