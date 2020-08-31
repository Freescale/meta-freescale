# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2020 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=228c72f2a91452b8a03c4cab30f30ef9"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "eabb27d28bba375a9f14d6306c07af5f"
SRC_URI[sha256sum] = "5ca1f8b6e6b191594d17ac7b51c38cdbc2ff808d15568ab4b0bbeb5dc6c621f6"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
