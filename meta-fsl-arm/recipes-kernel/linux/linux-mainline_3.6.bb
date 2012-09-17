# Copyright (C) 2012 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux mainline kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
DEPENDS += "lzop-native"

inherit kernel

require recipes-kernel/linux/linux-dtb.inc

PV = "3.5+3.6-rc6+git${SRCPV}"

# patches-3.6-rc6 tip
SRCREV = "ed4adce4a3aae5008af26c2ca6847dc56e850d31"

SRC_URI = "git://github.com/Freescale/linux-mainline.git \
           \
           file://defconfig"

S = "${WORKDIR}/git"

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
EXTRA_OEMAKE += "LOADADDR=${UBOOT_ENTRYPOINT}"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6)"
