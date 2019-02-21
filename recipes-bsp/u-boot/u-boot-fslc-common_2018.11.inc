# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

DEPENDS += "bison-native"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "6e25ce6f3c8568b9618de6569794a29a05e1b7a4"
SRCBRANCH = "2018.11+fslc"

PV = "v2018.11+git${SRCPV}"

S = "${WORKDIR}/git"
