# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2020 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=cf3f9b8d09bc3926b1004ea71f7a248a"

SRC_URI = " \
    ${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
"

SRC_URI[md5sum] = "3f8c4661ca0f64ca30fd239f66b6a471"
SRC_URI[sha256sum] = "2b70f169d4065b2a7ac7a676afe24636128bd2dacc9f5230346758c3b146b2be"

S = "${WORKDIR}/firmware-imx-${PV}"

inherit fsl-eula-unpack
