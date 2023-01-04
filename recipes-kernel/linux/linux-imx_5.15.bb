# Copyright 2013-2016 (C) Freescale Semiconductor
# Copyright 2017-2021 (C) NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "lzop-native bc-native"

# UPGRADE NOTE:
# The linux-imx kernel build uses a defconfig maintained in the layer. That
# defconfig is a copy of a defconfig maintained by NXP in the source.
# | SOURCE                              | LAYER                           |
# | arch/arm/configs/imx_v7_defconfig   | linux-imx/imx-nxp-bsp/defconfig |
# | arch/arm64/configs/imx_v8_defconfig | linux-imx/mx8-nxp-bsp/defconfig |
SRCBRANCH = "lf-5.15.y"
LOCALVERSION = "-5.15.71-2.2.0"
SRCREV = "3313732e9984cb8a6b10a9085c7e18d58e770d56"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "5.15.71"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
