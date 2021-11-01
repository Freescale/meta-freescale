# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=e565271ec9a80ce47abbddc4bffe56fa"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "97eb12ba899bc29a5375993889708bfb"
SRC_URI[sha256sum] = "8b7aa9b41a0389e5f40bdf91cb1224f79ea4c2e7fc36292f27ad4bef7044d7f0"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
