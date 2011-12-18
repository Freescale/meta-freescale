# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r0"

inherit kernel
COMPATIBLE_MACHINE = "(imx53qsb|imx53ard)"

#BRANCH="delphi_sbx"

SRC_URI = "git://sw-git.freescale.net/FPS/linux-imx.git;protpcol=git"
S = "${WORKDIR}/git"
