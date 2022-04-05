# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

DEPENDS += "flex-native bison-native"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH};protocol=https"

SRCREV = "f88519827335b548fcd276acda32107f5120276f"
SRCBRANCH = "2022.04+fslc"

PV = "v2022.04+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
