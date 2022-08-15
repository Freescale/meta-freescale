# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=d3c315c6eaa43e07d8c130dc3a04a011"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "9ed2923c0eb511c7fcf37dd607944124"
SRC_URI[sha256sum] = "65f829a9e2597bffc58a680aaefa638122144a083633d1ae09b3aec1d9f8ab84"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
