# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=3c3fe2b904fd694f28d2f646ee16dddb"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "1a9414928c3f30e7a911700fe83cd62e"
SRC_URI[sha256sum] = "da415c32063c08fce8f52734f198b19ab06bd7d4333a4df900f8831df562f8fc"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
