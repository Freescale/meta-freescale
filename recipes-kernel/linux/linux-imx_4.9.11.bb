# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_4.9.11_1.0.0_ga"
LOCALVERSION = "-1.0.0"
SRCREV = "c27010d99a3d91703ea2d1a3f9630a9dedc3f86f"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7)"
