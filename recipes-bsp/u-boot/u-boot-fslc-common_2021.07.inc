# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

DEPENDS += "flex-native bison-native"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "587e796995604fb669a3552daae682b4d6d346d9"
SRCBRANCH = "2021.07+fslc"

PV = "v2021.07+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
