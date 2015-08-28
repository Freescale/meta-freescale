DESCRIPTION = "UIO driver for T1040 L2 Switch"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "git://git.freescale.com/ppc/sdk/l2switch-uio.git;branch=master"
SRCREV = "80de8322d0ab8c28f5dfbe3cde8759a1efbe06c9"

inherit module

S = "${WORKDIR}/git/uio-driver"

COMPATIBLE_MACHINE = "(t1040|t1042)"
