DESCRIPTION = "Ethernet Configuration Files"
SECTION = "eth-config"
LICENSE = "BSD & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ed5eddbfbb84af5089ea94c382d423c"

PR = "r2"

SRC_URI = "git://git.freescale.com/ppc/sdk/eth-config.git;branch=master"
SRCREV = "1d6ad160fda9123e93c65141aa1578b6253ff7bc"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "D=${D}"

do_install() {
    oe_runmake install
}
