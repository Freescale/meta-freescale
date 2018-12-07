# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

PV = "5.5+git${SRCPV}"
SRC_URI = "git://github.com/NXPmicro/imx-kobs.git;protocol=https \
           file://0001-Add-missing-includes-as-pointed-out-by-musl.patch \
"
SRCREV = "a0e9adce2fb7fcd57e794d7f9a5deba0f94f521b"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

COMPATIBLE_MACHINE = "(imx)"
