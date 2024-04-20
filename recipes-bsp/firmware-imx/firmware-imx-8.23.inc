# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2022 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=44a8052c384584ba09077e85a3d1654f"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "eb03efb3a8fb98d99ed1df88537e72f4"
SRC_URI[sha256sum] = "fe07633295dac3dd99f0ba4e07a7cde95bb24a29ca82b9a9b3f6026d29ae916a"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
