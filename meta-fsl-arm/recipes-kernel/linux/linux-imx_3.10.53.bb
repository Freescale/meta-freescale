# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.10.53_1.1.0_ga"
LOCALVERSION = "-1.1.0_ga"
SRCREV = "496fbe0b831b77d0ea0a2c7bbd1d58820b3a01f7"

SRC_URI += "file://0001-ARM-clk-imx6q-fix-video-divider-for-revision-1.0-of-.patch \
            file://0002-ARM-imx6sl-Disable-imx6sl-specific-code-when-imx6sl-.patch \
"

COMPATIBLE_MACHINE = "(mx6)"
