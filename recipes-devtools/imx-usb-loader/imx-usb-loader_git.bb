DESCRIPTION = "i.MX/Vybrid recovery utility"
SECTION = "devel"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "libusb1"

SRCREV = "f009770d841468204ab104bf7d3b0c5bc8425dbb"
SRC_URI = "git://github.com/boundarydevices/imx_usb_loader.git;protocol=http"

PV = "1.0+${SRCPV}"

S = "${WORKDIR}/git"

do_install () {
	oe_runmake DESTDIR=${D} install
}

inherit pkgconfig

BBCLASSEXTEND = "native nativesdk"
