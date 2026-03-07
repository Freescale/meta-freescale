# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2021,2024 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
require imx-codec.inc
inherit fsl-eula-unpack

LIC_FILES_CHKSUM = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

IMX_SRCREV_ABBREV = "464e9d8"
IMX_PACKAGE_VERSION = "${PV}"

# FIX 1: Define the package name variable (Required for SRC_URI)
IMX_PACKAGE_NAME = "${BP}-${IMX_SRCREV_ABBREV}"

SRC_URI = "${FSL_MIRROR}/${IMX_PACKAGE_NAME}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "82242219e0d3536aeff31d9c215543bfe5541540bdb4fa8d61e83067b015e31f"

S = "${UNPACKDIR}/${IMX_PACKAGE_NAME}"
