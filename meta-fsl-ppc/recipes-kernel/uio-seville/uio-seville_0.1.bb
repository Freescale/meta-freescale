DESCRIPTION = "UIO driver for T1040 L2 Switch"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "git://git.freescale.com/ppc/sdk/l2switch-uio.git"
SRCREV = "1077880ff3557d44dc5dd9fe5e2f4ee1474fb25f"

inherit module

S = "${WORKDIR}/git/uio-driver"

COMPATIBLE_MACHINE = "(t1040qds|t1040rdb|t1040rdb-64b)"
