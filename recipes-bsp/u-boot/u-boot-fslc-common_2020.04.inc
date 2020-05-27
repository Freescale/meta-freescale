# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

DEPENDS += "bison-native"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "a266838009e02b369b51f4139c6ac64facb12d34"
SRCBRANCH = "2020.04+fslc"

PV = "v2020.04+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
