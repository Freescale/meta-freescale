# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=fd4b227530cd88a82af6a5982cfb724d"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "0b509fe540a9678ba028cdb03954d545"
SRC_URI[sha256sum] = "a7f4a22c05a990aad43a616e312847ab9d85ce00691b34fa8590d5c7c1d722e9"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
