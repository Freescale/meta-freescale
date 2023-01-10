# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "436501f8e67c10eed6bba88457e701a2"
SRC_URI[sha256sum] = "f050f60351a54fd75954fdeb133ddd014a5576040c72616e216b63db0e242dd4"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
