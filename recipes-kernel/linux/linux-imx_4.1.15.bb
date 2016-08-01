# Copyright (C) 2013-2016 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_4.1.15_1.0.0_ga"
LOCALVERSION = "-1.2.0"
SRCREV = "77f61547834c4f127b44b13e43c59133a35880dc"

# Add patches for gcc 6 compiler issue
SRC_URI += "file://gcc6_integrate_fix.patch \
            file://bcmhd_gcc6_indent_warning_error_fix.patch \
            file://gpu-viv_gcc6_indent_warning_error_fix.patch"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
