DESCRIPTION = "Ethernet Configuration Files"
SECTION = "eth-config"
LICENSE = "BSD & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ed5eddbfbb84af5089ea94c382d423c"

PR = "r2"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/eth-config;nobranch=1"
SRCREV = "e3dcd110638dab004bcc759f6f51a0994bdfd8d5"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "D=${D}"

do_install() {
    oe_runmake install
    chown -R root:root ${D}
}

CLEANBROKEN = "1"
