# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ab1a30d0cd181e3408077727ea5a2db"

SRCBRANCH ?= "master"
SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "0967aa59b3fd8d80fcb98146a9aac91b"
SRC_URI[sha256sum] = "910fbf866f61185adfd60c1704b2da41030cb175901d06e40402b49f9240bdee"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
