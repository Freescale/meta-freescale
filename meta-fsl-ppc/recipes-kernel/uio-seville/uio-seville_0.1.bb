DESCRIPTION = "UIO driver for T1040 L2 Switch"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "git://git.freescale.com/ppc/sdk/l2switch-uio.git;branch=sdk-v1.7.x"
SRCREV = "35af73f3ba00745777f32787400d9eb0317d7ff5"

inherit module

S = "${WORKDIR}/git/uio-driver"

COMPATIBLE_MACHINE ?= "(none)"
COMPATIBLE_MACHINE_t1040 = ".*"
COMPATIBLE_MACHINE_t1042 = ".*"

