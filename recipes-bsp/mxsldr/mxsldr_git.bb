# Copyright (C) 2012 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale i.MX233/i.MX28 USB loader"
DEPENDS = "libusb1"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "c40d80472525e1d57dae5317c028b745968c0399"
SRC_URI = "git://git.denx.de/mxsldr.git;branch=master \
           file://0001-Do-not-ignore-OE-cflags-and-ldflags.patch \
           "

PV = "0.0.0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit pkgconfig

do_compile() {
	oe_runmake
}
do_install() {
	oe_runmake install DESTDIR="${D}"
}
BBCLASSEXTEND = "native nativesdk"
