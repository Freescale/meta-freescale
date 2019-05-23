# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2017-2019 NXP
SUMMARY = "A Daemon wait for NXP mfgtools host's command"
SECTION = "base"
DEPENDS = "virtual/kernel dosfstools-native"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit autotools-brokensep

PR = "r1"
PV = "0.5.1+git${SRCPV}"

SRC_URI = "git://github.com/NXPmicro/imx-uuc.git;protocol=https"
SRCREV = "fc48b497fe961d601b4bcced807f562090854ec9"

S = "${WORKDIR}/git"

FILES_${PN} += "/linuxrc /fat"

COMPATIBLE_MACHINE = "(imx)"
