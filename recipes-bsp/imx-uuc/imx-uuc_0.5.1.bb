# Copyright (C) 2014-2015 Freescale Semiconductor
# Copyright (C) 2014 O.S. Systems Software LTDA.

SUMMARY = "Universal Adapter source"
SECTION = "base"
DEPENDS = "virtual/kernel dosfstools-native"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "f2edde0da371f89c7471884eb1e5143a"
SRC_URI[sha256sum] = "95d7f7b075f58fedc07602fbc0e5bf5212d28deea3d09cdbcbd80992dcd18482"

inherit autotools-brokensep

FILES_${PN} += "/linuxrc /fat"
