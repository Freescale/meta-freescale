# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=6c12031a11b81db21cdfe0be88cac4b3"

SRCBRANCH ?= "master"
SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "a6b926d087e1e642580da8a6d982abe6"
SRC_URI[sha256sum] = "45b0a7392e7a4b2e6c7e1cb988dd660ff3f8cd465afdf796ff840a00db08389d"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
