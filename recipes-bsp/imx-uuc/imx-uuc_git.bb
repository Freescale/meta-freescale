# Copyright (C) 2016 Freescale Semiconductor

SUMMARY = "A Daemon wait for Freescale/NXP mfgtools host's command"
SECTION = "base"
DEPENDS = "virtual/kernel dosfstools-native"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit autotools-brokensep

PR = "r1"
PV = "0.5.1+git${SRCPV}"

SRC_URI = "git://github.com/NXPmicro/imx-uuc.git;protocal=https"
SRCREV = "3440b1108373e79669cc17ba1d2be91a123a2053"

S = "${WORKDIR}/git"

FILES_${PN} += "/linuxrc /fat"
