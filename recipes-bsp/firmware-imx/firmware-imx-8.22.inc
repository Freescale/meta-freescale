# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2022 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=2827219e81f28aba7c6a569f7c437fa7"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "c5cf3842569f0a7fd990fbc64979e84f"
SRC_URI[sha256sum] = "94c8bceac56ec503c232e614f77d6bbd8e17c7daa71d4e651ea8fd5034c30350"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
