SUMMARY = "i.MX/Vybrid USB recovery utility"
DESCRIPTION = "Utility to load and run programs on i.MX/Vybrid SoCs over USB using the serial download protocol"
HOMEPAGE = "https://github.com/boundarydevices/imx_usb_loader"
SECTION = "devel"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "libusb1"

PV = "1.0+${SRCPV}"
SRC_URI = "git://github.com/boundarydevices/imx_usb_loader.git;protocol=https;branch=master"
SRCREV = "f009770d841468204ab104bf7d3b0c5bc8425dbb"

do_install () {
    oe_runmake DESTDIR=${D} install
}

inherit pkgconfig

BBCLASSEXTEND = "native nativesdk"
