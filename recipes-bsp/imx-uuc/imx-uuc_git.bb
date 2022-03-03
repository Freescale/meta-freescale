# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2017-2019 NXP
SUMMARY = "A Daemon wait for NXP mfgtools host's command"
SECTION = "base"
DEPENDS = "virtual/kernel dosfstools-native"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit autotools-brokensep

PR = "r1"
PV = "0.5.1+git${SRCPV}"

SRC_URI = "git://github.com/NXPmicro/imx-uuc.git;protocol=https;branch=master"
SRCREV = "d6afb27e55d73d7ad08cd2dd51c784d8ec9694dc"

S = "${WORKDIR}/git"

FILES:${PN} += "/linuxrc /fat"

COMPATIBLE_MACHINE = "(imx-generic-bsp)"
