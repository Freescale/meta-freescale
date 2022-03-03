DESCRIPTION = "i.MX Register tool"
SECTION = "devel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=5003fa041d799dd5dd5f646b74e36924"

SRCREV = "dcc3e3f26d3d867d5297a104dc32bd99f5e6fa71"
SRC_URI = "git://github.com/boundarydevices/devregs.git;protocol=https;branch=master"

PV = "1.0+${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools
