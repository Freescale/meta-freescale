# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

DEPENDS += "flex-native bison-native"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "b047e1dcd20a2a767aa72aba8a040b16903f261f"
SRCBRANCH = "2021.07+fslc"

PV = "v2021.07+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
