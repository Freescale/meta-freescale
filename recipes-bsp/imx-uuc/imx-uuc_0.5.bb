# Copyright (C) 2014 Freescale Semiconductor
# Copyright (C) 2014 O.S. Systems Software LTDA.

SUMMARY = "Universal Adapter source"
SECTION = "base"
DEPENDS = "virtual/kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "4a82f106f85199c91a058ee54c127477"
SRC_URI[sha256sum] = "ec5894ab7967797c8500b4988d0aba6c6f78ef22b4a190f9db12be33df08faaf"

inherit autotools-brokensep

FILES_${PN} += "/linuxrc"
