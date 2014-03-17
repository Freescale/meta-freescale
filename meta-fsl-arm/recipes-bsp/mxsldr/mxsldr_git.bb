# Copyright (C) 2012 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale i.MX233/i.MX28 USB loader"
DEPENDS = "libusb"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "c40d80472525e1d57dae5317c028b745968c0399"
SRC_URI = "git://git.denx.de/mxsldr.git"

PV = "0.0.0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools-brokensep

BBCLASSEXTEND = "native nativesdk"
