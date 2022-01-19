# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2021 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=03bcadc8dc0a788f66ca9e2b89f56c6f"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "3357c84e48fdc220984a9642d1e808f6"
SRC_URI[sha256sum] = "bfe9c57857e8442e7eb26ba3e1020733b09a7c9b83952ad4822980546c58a7f4"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
