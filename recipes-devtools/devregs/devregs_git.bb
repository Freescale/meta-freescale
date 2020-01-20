DESCRIPTION = "i.MX Register tool"
SECTION = "devel"
LICENSE = "GPL-1"
LIC_FILES_CHKSUM = "file://COPYING;md5=5003fa041d799dd5dd5f646b74e36924"

SRCREV = "6d2ad752ce92ad37ab144d9c3322f3dcd43398e5"
SRC_URI = "git://github.com/boundarydevices/devregs.git;protocol=http \
           file://0001-devregs-add-support-for-imx8mn-soc.patch \
"

PV = "1.0+${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools
