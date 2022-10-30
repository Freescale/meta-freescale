# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "63eac7b400d711c668458ec4bf5d0c60"
SRC_URI[sha256sum] = "1ee3c49ad8749867487f09d6e4472536fb809b667c1bb3c56511175b8974e3c6"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
