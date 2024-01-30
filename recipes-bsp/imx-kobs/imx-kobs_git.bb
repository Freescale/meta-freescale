# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2019,2024 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=393a5ca445f6965873eca0259a17f833"

PV = "5.5+git${SRCPV}"
SRC_URI = "git://github.com/NXPmicro/imx-kobs.git;protocol=https;branch=master \
           file://0001-Add-missing-includes-as-pointed-out-by-musl.patch \
"
SRCREV = "ff13a99a22aa73cca0e09a33c2ebb6a94ad698da"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

COMPATIBLE_MACHINE = "(imx-generic-bsp)"
