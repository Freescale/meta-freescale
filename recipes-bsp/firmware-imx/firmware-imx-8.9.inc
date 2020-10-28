# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2020 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=983e4c77621568488dd902b27e0c2143"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "7b36242be60a882f9b5f74bb6e2c6aa1"
SRC_URI[sha256sum] = "a72f70fd2ecaa58800bb88ed672fddc322ab9843ee7777eb89b82016b0aa3614"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
