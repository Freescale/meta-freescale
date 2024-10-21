# Copyright 2013-2016 (C) Freescale Semiconductor
# Copyright 2017-2024 (C) NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

SRCBRANCH = "lf-6.6.y"
LOCALVERSION = "-6.6.36-2.1.0"
SRCREV = "d23d64eea5111e1607efcce1d601834fceec92cb"

SRC_URI += " \
    file://0001-tty-vt-conmakehash-Don-t-mention-the-full-path-of-th.patch \
    file://0001-lib-build_OID_registry-don-t-mention-the-full-path-o.patch \
    file://0001-video-logo-Drop-full-path-of-the-input-filename-in-g.patch \
"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.6.36"

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "imx_v8_defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "imx_v8_defconfig"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
