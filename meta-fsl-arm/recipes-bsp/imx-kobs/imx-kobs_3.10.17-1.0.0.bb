# Copyright (C) 2013, 2014 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "${FSL_MIRROR}/imx-kobs-${PV}.tar.gz"

SRC_URI[md5sum] = "df477a6ce9abfa3077fa1a7bb97942e1"
SRC_URI[sha256sum] = "9458bac13a8e58e3d13cb2861d2b3d2cffd990e675c919c65548b71b6dda2776"

inherit  autotools pkgconfig
