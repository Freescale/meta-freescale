# Copyright (C) 2016 Freescale Semiconductor

SUMMARY = "A Daemon wait for Freescale/NXP mfgtools host's command"
SECTION = "base"
DEPENDS = "dosfstools-native"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit autotools-brokensep

PV = "0.5.1+git${SRCPV}"

SRC_URI = "git://github.com/NXPmicro/imx-uuc.git;protocol=https"
SRCREV = "e3fbdfef978abd150d9ea71c4d174daded9c8d33"

S = "${WORKDIR}/git"

FILES_${PN} += "/linuxrc /fat"

COMPATIBLE_MACHINE = "(imx)"
