# Copyright 2017-2021 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx7d-nxp-bsp = "file://COPYING;md5=8cf95184c220e247b9917e7244124c5a"

# This legacy mx7d release ships as "${SOC}-m4-freertos-${PV}.bin", not the
# "${SOC}-${MCORE_TYPE}-demo-${PV}.bin" that imx-mcore-demos.inc builds, so
# SRC_URI and S are fully replaced here. This is a genuine per-release value,
# not an addition (+= does not apply), and it cannot be a weak default: both
# SRC_URI and S are set with a hard "=" in bitbake.conf, so "?=" in the include
# would leave that config default in place for the other releases. JUSTIFIED.
# nooelint: oelint.var.override
SRC_URI = "${FSL_MIRROR}/${SOC}-m4-freertos-${PV}.bin;fsl-eula=true"
# nooelint: oelint.var.override
S = "${UNPACKDIR}/${SOC}-m4-freertos-${PV}"

SRC_URI[sha256sum] = "cc00d3b936d49b2794a2a99e10129437e70caba3fd26b8379b8c50dd22f73254"

COMPATIBLE_MACHINE = "(mx7d-nxp-bsp)"
