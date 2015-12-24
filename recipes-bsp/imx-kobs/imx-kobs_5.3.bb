# Copyright (C) 2013-2015 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "${FSL_MIRROR}/imx-kobs-${PV}.tar.gz"

SRC_URI[md5sum] = "a2a9e1c3445d14c961577492313a41fb"
SRC_URI[sha256sum] = "45f729fc2b49556f1ca9df778f52bf5cc749cfe53664c8206daab29991c5f6c1"

inherit  autotools pkgconfig

COMPATIBLE_MACHINE = "(imx)"
