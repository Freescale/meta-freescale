# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.14.28_1.0.0_ga"
LOCALVERSION = "-1.0.0_ga"
SRCREV = "91cf351a2afc17ac4a260e4d2ad1e32d00925a1b"

SRC_URI += " \
    file://0001-ARM-imx6q-drop-unnecessary-semicolon.patch \
    file://0002-ARM-clk-imx6q-fix-video-divider-for-rev-T0-1.0.patch \
    file://0003-ARM-imx6sl-Disable-imx6sl-specific-code-when-imx6sl-.patch \
    file://0004-mmc-sdhci-esdhc-imx-Fixup-runtime-PM-conditions-duri.patch \
    file://0005-Revert-net-fec-fix-the-warning-found-by-dma-debug.patch \
"

COMPATIBLE_MACHINE = "(mx6)"
