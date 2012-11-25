# Copyright (C) 2012 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale i.MX233/i.MX28 USB loader"
DEPENDS = "libusb"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "3463b576b67f03012a59cedd8e55e9d37c5cea76"
SRC_URI = "git://git.bfuser.eu/git/marex/mxsldr.git;protocol=http"

PV = "0.0.0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools

BBCLASSEXTEND = "native nativesdk"
