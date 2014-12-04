# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.10.31_1.1.0_beta"
SRCREV = "94a7e472c47b800c001c0a5d17bb2f14a13e1980"
LOCALVERSION = "-1.1.0_beta"

SRC_URI += "file://0001-ARM-clk-imx6q-fix-video-divider-for-revision-1.0-of-.patch"

COMPATIBLE_MACHINE = "(mx6)"
