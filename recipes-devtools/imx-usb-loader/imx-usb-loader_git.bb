DESCRIPTION = "i.MX/Vybrid recovery utility"
SECTION = "devel"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "libusb"

SRCREV = "349286e25c3fd9b2d31b31e962340123bbc62d44"
SRC_URI = "git://github.com/boundarydevices/imx_usb_loader.git;protocol=http"

PV = "1.0+${SRCPV}"

S = "${WORKDIR}/git"

do_install () {
	oe_runmake DESTDIR=${D} install
}

BBCLASSEXTEND = "native nativesdk"
