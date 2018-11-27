# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "1f39b911b7817efb801f80784d9c7a41704b7a36"
SRCBRANCH = "2018.11+fslc"

PV = "v2018.11+git${SRCPV}"

S = "${WORKDIR}/git"
