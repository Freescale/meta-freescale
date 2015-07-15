DESCRIPTION = "i.MX Register tool"
SECTION = "devel"
LICENSE = "GPL-1"
LIC_FILES_CHKSUM = "file://COPYING;md5=5003fa041d799dd5dd5f646b74e36924"

SRCREV = "34ed402b92920864b89e0fd1e76bae3aa340baaa"
SRC_URI = "git://github.com/boundarydevices/devregs.git;protocol=http"

PV = "1.0+${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools
