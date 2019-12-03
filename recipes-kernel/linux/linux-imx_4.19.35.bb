# Copyright 2013-2016 (C) Freescale Semiconductor
# Copyright 2017-2019 (C) NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_4.19.35_1.1.0"
LOCALVERSION = "-1.1.0"
SRCREV = "0f9917c56d5995e1dc3bde5658e2d7bc865464de"
SRC_URI += "file://0001-Compiler-Attributes-add-support-for-__copy-gcc-9.patch \
            file://0002-include-linux-module.h-copy-__init-__exit-attrs-to-i.patch \
           "

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
