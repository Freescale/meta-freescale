# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH_mx6q = "imx_3.14.38_6qp_ga"
LOCALVERSION_mx6q = "-6QuadPlus"
SRCREV_mx6q = "75a01115a7f53d728e6abeb7b205676cc2a50bc0"

SRCBRANCH_mx6dl = "imx_3.14.38_6qp_ga"
LOCALVERSION_mx6dl = "-6DualLite"
SRCREV_mx6dl = "75a01115a7f53d728e6abeb7b205676cc2a50bc0"

SRCBRANCH_mx6sx = "imx_3.14.38_6qp_ga"
LOCALVERSION_mx6sx = "-6SoloX"
SRCREV_mx6sx = "75a01115a7f53d728e6abeb7b205676cc2a50bc0"

SRCBRANCH_mx6sl = "imx_3.14.38_6qp_ga"
LOCALVERSION_mx6sl = "-6SOloLite"
SRCREV_mx6sl = "75a01115a7f53d728e6abeb7b205676cc2a50bc0"

SRCBRANCH_mx6ul = "imx_3.14.38_6ul_ga"
LOCALVERSION_mx6ul = "-6UltraLite"
SRCREV_mx6ul = "e4944a51c4bb950a8f13e421eefc000a87350e23"

SRCBRANCH_mx7 = "imx_3.14.38_6ul_ga"
LOCALVERSION_mx7 = "-7Dual_beta"
SRCREV_mx7 = "e4944a51c4bb950a8f13e421eefc000a87350e23"

SRC_URI += " \
    file://0004-mmc-sdhci-esdhc-imx-Fixup-runtime-PM-conditions-duri.patch \
    file://0001-ARM-LLVMLinux-Change-extern-inline-to-static-inline.patch \
    file://0001-ARM-8158-LLVMLinux-use-static-inline-in-ARM-ftrace.patch \
"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
