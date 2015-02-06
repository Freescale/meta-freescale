# Copyright (C) 2012-2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${SRCBRANCH} \
           file://defconfig"
LOCALVERSION = "-fslc"
