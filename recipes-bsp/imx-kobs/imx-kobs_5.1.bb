# Copyright (C) 2013-2015 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "${FSL_MIRROR}/imx-kobs-${PV}.tar.gz"

SRC_URI[md5sum] = "b3217106d2ea7da2774835bf264633f1"
SRC_URI[sha256sum] = "28c7356187ae26a2b37fb37a4b7bfdb63b3628b72d737d897ae6d5b969fbb51a"

inherit  autotools pkgconfig

COMPATIBLE_MACHINE = "(imx)"
