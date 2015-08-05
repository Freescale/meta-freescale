# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH_mx6q = "imx_3.14.38_6qp_beta"
LOCALVERSION_mx6q = "-6QuadPlus_beta"
SRCREV_mx6q = "a4dec7730c98f2639fe250a1a67f782b6f73bdf5"

SRCBRANCH_mx6dl = "imx_3.14.38_6qp_beta"
LOCALVERSION_mx6dl = "-6DualLite"
SRCREV_mx6dl = "a4dec7730c98f2639fe250a1a67f782b6f73bdf5"

SRCBRANCH_mx6sx = "imx_3.14.38_6qp_beta"
LOCALVERSION_mx6sx = "-6SoloX"
SRCREV_mx6sx = "a4dec7730c98f2639fe250a1a67f782b6f73bdf5"

SRCBRANCH_mx6sl = "imx_3.14.38_6qp_beta"
LOCALVERSION_mx6sl = "-6SOloLite_beta"
SRCREV_mx6sl = "a4dec7730c98f2639fe250a1a67f782b6f73bdf5"

SRCBRANCH_mx6ul = "imx_3.14.38_6ul7d_beta"
LOCALVERSION_mx6ul = "-6UltraLite_beta"
SRCREV_mx6ul = "1b058645ab95717db8df7de389e1c725dc0c49cc"

SRCBRANCH_mx7 = "imx_3.14.38_6ul7d_beta"
LOCALVERSION_mx7 = "-7Dual_beta"
SRCREV_mx7 = "1b058645ab95717db8df7de389e1c725dc0c49cc"

SRC_URI += " \
    file://0004-mmc-sdhci-esdhc-imx-Fixup-runtime-PM-conditions-duri.patch \
"

COMPATIBLE_MACHINE = "(mx6|mx7)"
