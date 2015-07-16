# Copyright (C) 2013-2015 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "${FSL_MIRROR}/imx-kobs-${PV}.tar.gz"

SRC_URI[md5sum] = "a5ae42bdc5daefa3c2f939f860abfd20"
SRC_URI[sha256sum] = "68402230910b9f82690988eb6deb612f724f2dbc8aa6f9376714062b898e5702"

inherit  autotools pkgconfig

COMPATIBLE_MACHINE = "(imx)"
