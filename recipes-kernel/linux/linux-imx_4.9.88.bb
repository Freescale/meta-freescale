# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_4.9.88_imx8qxp_beta2"
LOCALVERSION = "-${SRCBRANCH}"
SRCREV = "05f46d3f9b4a8987edac62a52528d52f47cfa42d"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
