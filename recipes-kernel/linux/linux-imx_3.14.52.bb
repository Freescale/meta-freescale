# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.14.52_1.1.0_ga"
LOCALVERSION = "-1.1.0_ga"
SRCREV = "5f6f0a50e6039370078369ddf380179d59024789"

SRC_URI += " \
    file://0004-mmc-sdhci-esdhc-imx-Fixup-runtime-PM-conditions-duri.patch \
"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
