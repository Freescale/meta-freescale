# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

DEPENDS += "bison-native"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "5003fc093c40895e541fe481016bd3220078a81c"
SRCBRANCH = "2020.10+fslc"

PV = "v2020.10+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
