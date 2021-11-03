DESCRIPTION = "i.MX Register tool"
SECTION = "devel"
LICENSE = "GPL-1"
LIC_FILES_CHKSUM = "file://COPYING;md5=5003fa041d799dd5dd5f646b74e36924"

SRCREV = "d5f6223027f4d6ae71bd5d432f5611486e0e6074"
SRC_URI = "git://github.com/boundarydevices/devregs.git;protocol=https;branch=master"

PV = "1.0+${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools
