# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=fb0303e4ee8b0e71c094171e2272bd44"

SRCBRANCH ?= "master"
SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "b494c9a2ba9f8906de73ac33068789a2"
SRC_URI[sha256sum] = "c115d5d0d115fc14754671bd2428f672f9f99677988588accef2fbca2c3f749c"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
