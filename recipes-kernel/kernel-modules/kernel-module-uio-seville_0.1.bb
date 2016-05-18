DESCRIPTION = "UIO driver for T1040 L2 Switch"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "git://git.freescale.com/ppc/sdk/l2switch-uio.git;branch=sdk-v2.0.x"
SRCREV = "0f31fbcbe9ab1ab9c424da34f70c82314b16f8de"

inherit module

S = "${WORKDIR}/git/uio-driver"

COMPATIBLE_MACHINE = "(t1040|t1042)"
