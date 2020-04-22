# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2019 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

PV = "5.5+git${SRCPV}"
SRC_URI = "git://github.com/NXPmicro/imx-kobs.git;protocol=https \
"
SRCREV = "c50b0a001d506a8c39c206b26ec24e71fcf2afb6"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

COMPATIBLE_MACHINE = "(imx)"
