# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

DEPENDS += "flex-native bison-native"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH};protocol=https"

SRCREV = "ed9503daa309c765a8654dc89c486326b4850f6d"
SRCBRANCH = "2022.01+fslc"

PV = "v2022.01+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
