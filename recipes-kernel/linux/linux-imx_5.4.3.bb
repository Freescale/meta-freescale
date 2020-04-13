# Copyright 2013-2016 (C) Freescale Semiconductor
# Copyright 2017-2019 (C) NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "lf-5.4.y"
LOCALVERSION = "-1.0.0"
SRCREV = "f8118585ee3c7025265b28985fdfe0af96a84466"

SRC_URI += "file://0001-perf-Make-perf-able-to-build-with-latest-libbfd.patch"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
