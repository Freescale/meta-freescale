# Copyright (C) 2012-2017 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "a6a15fedd1a529936b0a46ade1d776740dbef70f"
SRCBRANCH = "2017.09+fslc"

PV = "v2017.09+git${SRCPV}"

S = "${WORKDIR}/git"
