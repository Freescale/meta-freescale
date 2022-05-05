# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=be5ff43682ed6c57dfcbeb97651c2829"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "ef91390da6c8a6a48c8121a5dd667de8"
SRC_URI[sha256sum] = "937e196476b8e95b4b7f2501a14c8326d8a0649f8a3f9228b72373770a08deb3"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
