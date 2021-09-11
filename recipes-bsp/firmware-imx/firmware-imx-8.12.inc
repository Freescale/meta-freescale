# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=417b82f17fc02b88125331ed312f6f1b"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "6062247e9f12f3ec27247079e59fb935"
SRC_URI[sha256sum] = "6b6747bf36ecc53e385234afdce01f69c5775adf0d6685c885281ca6e4e322ef"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
