# Copyright (C) 2012 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX233/i.MX28 USB loader"
DESCRIPTION = "Freescale i.MX233/i.MX28 USB loader"
HOMEPAGE = "https://source.denx.de/denx/mxsldr"
SECTION = "devel"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
DEPENDS = "libusb1"

PV = "0.0.0+git${SRCPV}"
SRC_URI = "git://source.denx.de/denx/mxsldr.git;branch=master;protocol=https \
           file://0001-Do-not-ignore-OE-cflags-and-ldflags.patch \
           "
SRCREV = "c40d80472525e1d57dae5317c028b745968c0399"

inherit pkgconfig

do_compile() {
    oe_runmake
}
do_install() {
    oe_runmake install DESTDIR="${D}"
}
BBCLASSEXTEND = "native nativesdk"
