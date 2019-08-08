# Copyright 2013-2016 (C) Freescale Semiconductor
# Copyright 2017-2019 (C) NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_4.14.98_2.0.0_ga"
LOCALVERSION = "-imx"
SRCREV = "1175b59611537b0b451e0d1071b1666873a8ec32"
SRC_URI += "file://0001-compiler-attributes-add-support-for-copy-gcc-9.patch \
            file://0002-include-linux-module.h-copy-init-exit-attrs-to-.patch \
           "

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
