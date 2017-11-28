# Copyright (C) 2012-2017 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

SRCREV = "ca0c3f3fac33bcb1c26ed399fdd89838a3bd5914"
SRCBRANCH = "2017.11+fslc"

PV = "v2017.11+git${SRCPV}"

S = "${WORKDIR}/git"
