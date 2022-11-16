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
SRCREV = "61f258fb6f10f690633eb068a8d4aaaba5e5e8a2"

S = "${WORKDIR}/git"

FILES:${PN} += "/linuxrc /fat"

COMPATIBLE_MACHINE = "(imx-generic-bsp)"
