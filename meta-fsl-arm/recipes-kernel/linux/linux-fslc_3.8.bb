# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux mainline kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
DEPENDS += "lzop-native"
PROVIDES = "virtual/kernel linux-mainline"

inherit kernel

require recipes-kernel/linux/linux-dtb.inc

PV = "3.8+git${SRCPV}"
PR = "r5"

# patches-3.8
SRCREV = "9674b8d96fca62bc47be48fd857591dcfca874b1"

SRC_URI = "git://github.com/Freescale/linux-mainline.git \
           \
           file://defconfig"

S = "${WORKDIR}/git"

do_configure_append () {
    # Ensure we have a proper GIT hash in kernel version
    rm ${S}/.scmversion
}

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
EXTRA_OEMAKE += "LOADADDR=${UBOOT_ENTRYPOINT}"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
