DESCRIPTION = "Ethernet Configuration Files"
SECTION = "eth-config"
LICENSE = "BSD & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=47716bd5b656aa5e298a132a64d2d1e4"

PR = "r2"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/eth-config;nobranch=1"
SRCREV = "eefb06353c8c3db34d22c26825b66e921bce617d"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "D=${D}"

do_install() {
    oe_runmake install
    chown -R root:root ${D}
}

CLEANBROKEN = "1"
