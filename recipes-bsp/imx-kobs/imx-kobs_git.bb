# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

PV = "5.5+git${SRCPV}"
SRCREV = "a2734b93759b4222f9dfa3f8c7645be9d39ea601"

SRC_URI = "git://github.com/NXPmicro/imx-kobs.git;protocal=https \
           file://0001-Add-missing-includes-as-pointed-out-by-musl.patch \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

COMPATIBLE_MACHINE = "(imx)"
