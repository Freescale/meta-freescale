# Copyright (C) 2013-2016 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

PV = "5.5+git${SRCPV}"
SRCREV = "b402243a04e5a6760a860445e5ff6a931d86f794"

SRC_URI = "git://github.com/NXPmicro/imx-kobs.git;protocal=https \
           file://fix-compile.patch \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

COMPATIBLE_MACHINE = "(imx)"
