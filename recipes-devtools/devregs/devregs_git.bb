SUMMARY = "i.MX register access tool"
DESCRIPTION = "Command-line tool to read and write i.MX SoC registers by name"
HOMEPAGE = "https://github.com/boundarydevices/devregs"
SECTION = "devel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=5003fa041d799dd5dd5f646b74e36924"

PV = "1.0+${SRCPV}"
SRC_URI = "git://github.com/boundarydevices/devregs.git;protocol=https;branch=master"
SRCREV = "dcc3e3f26d3d867d5297a104dc32bd99f5e6fa71"

inherit autotools
