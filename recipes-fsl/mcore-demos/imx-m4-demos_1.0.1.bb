# Copyright 2017-2021 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx7d-nxp-bsp = "file://COPYING;md5=8cf95184c220e247b9917e7244124c5a"

SRC_URI = "${FSL_MIRROR}/${SOC}-m4-freertos-${PV}.bin;fsl-eula=true"
S = "${WORKDIR}/${SOC}-m4-freertos-${PV}"

SRC_URI[sha256sum] = "cc00d3b936d49b2794a2a99e10129437e70caba3fd26b8379b8c50dd22f73254"

COMPATIBLE_MACHINE = "(mx7d-nxp-bsp)"
