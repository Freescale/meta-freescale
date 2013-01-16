# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux mainline kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
DEPENDS += "lzop-native"
PROVIDES = "virtual/kernel linux-mainline"

inherit kernel

require recipes-kernel/linux/linux-dtb.inc

PV = "3.7+git${SRCPV}"
PR = "r3"

# patches-3.7
SRCREV = "bc2b10c4b6edf5ce1fae6f69f7651c0e8f736195"

SRC_URI = "git://github.com/Freescale/linux-mainline.git \
           \
           file://defconfig"

S = "${WORKDIR}/git"

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
EXTRA_OEMAKE += "LOADADDR=${UBOOT_ENTRYPOINT}"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
