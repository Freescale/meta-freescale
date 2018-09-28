# Copyright (C) 2016 Freescale Semiconductor

SUMMARY = "A Daemon wait for Freescale/NXP mfgtools host's command"
SECTION = "base"
DEPENDS = "virtual/kernel dosfstools-native"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit autotools-brokensep

PV = "0.5.1+git${SRCPV}"

SRC_URI = "git://github.com/NXPmicro/imx-uuc.git;protocol=https"
SRCREV = "323efa5e4c799c40503ac92a8104c33b090e6731"

S = "${WORKDIR}/git"

FILES_${PN} += "/linuxrc /fat"

COMPATIBLE_MACHINE = "(imx)"
