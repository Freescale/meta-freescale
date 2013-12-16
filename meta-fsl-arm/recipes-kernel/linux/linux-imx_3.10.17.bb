# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

DEFAULT_PREFERENCE = "-1"

SRCBRANCH = "imx_3.10.17_1.0.0_beta"
SRCREV = "ec1af9f898d234001d8fc7d720382de34cb6580f"
LOCALVERSION = "-1.0.0_beta"

COMPATIBLE_MACHINE = "(mx6)"
