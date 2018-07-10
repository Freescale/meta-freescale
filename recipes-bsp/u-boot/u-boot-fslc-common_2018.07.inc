# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "bc558729a723f2d2c91b74d9c96716666550d528"
SRCBRANCH = "2018.07+fslc"

PV = "v2018.07+git${SRCPV}"

S = "${WORKDIR}/git"
