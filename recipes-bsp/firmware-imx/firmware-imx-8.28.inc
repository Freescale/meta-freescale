# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2025 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
HOMEPAGE = "https://www.nxp.com/"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

# Note: This .inc file is used from differently named recipes, so the package
# name must be hard-coded, i.e., ${BPN} cannot be used.
SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
IMX_SRCREV_ABBREV = "994fa14"
SRC_URI[sha256sum] = "55996f340e87825685a00cd309901189066ec9545ee607734f942c3cde4d69dc"

S = "${UNPACKDIR}/firmware-imx-${PV}-${IMX_SRCREV_ABBREV}"

inherit fsl-eula-unpack
