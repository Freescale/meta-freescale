# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.10.17_1.0.1_ga"
SRCREV = "dac46dcf913585956a0e7a838e6f4b7465f00f57"
LOCALVERSION = "-1.0.1_ga"

SRC_URI += "file://0001-ENGR00309838-ARM-imx6sl-gpc-add-chip-revision-check-.patch \
            file://0002-regulator-pfuze100-Fix-address-of-FABID.patch \
            file://0003-regulator-pfuze100-add-pfuze200-support.patch \
            file://0004-regulator-pfuze100-Add-terminate-entry-for-i2c-of-_d.patch \
            file://0005-regulator-pfuze100-Add-PFUZE200-support-to-Kconfig-a.patch \
            file://0006-regulator-pfuze100-Use-of_get_child_by_name.patch \
            file://0007-pfuze100-regulator-Fix-of_node_get-parameter.patch \
            file://0008-ENGR00301078-1-ARM-dts-imx6dl-sabresd-add-support-fo.patch \
            file://0009-ENGR00301078-2-ARM-dts-imx6sl-evk-add-support-for-pf.patch \
            file://0010-ENGR00318392-ARM-imx6x-Save-restore-SCU-and-some-CP1.patch"

COMPATIBLE_MACHINE = "(mx6)"
