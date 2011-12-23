# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r6"

inherit kernel
COMPATIBLE_MACHINE = "(imx53qsb|imx53ard)"

SRC_URI = "git://opensource.freescale.com/pub/scm/imx/linux-2.6-imx.git;tag=rel_imx_2.6.35_11.09.01;protocol=http \
           file://egalax_ts-enable-single-event-support.patch \
           file://plat-mxc-double-dma-zone-max.patch \
           file://devtmpfs-init-options-alignment.patch \
           file://defconfig \
          "

S = "${WORKDIR}/git"
