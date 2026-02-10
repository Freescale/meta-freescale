# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2025 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SECTION = "base"
LICENSE = "Proprietary"

# 1. Update Checksum to point to the EULA (matching the error log requirements)
LIC_FILES_CHKSUM = "file://EULA;md5=bc649096ad3928ec06a8713b8d787eac"

# Note: This .inc file is used from differently named recipes, so the package
# name must be hard-coded, i.e., ${BPN} cannot be used.

# 2. Add the local EULA file to SRC_URI
SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true \
           file://${FSL_EULA_FILE}"

IMX_SRCREV_ABBREV = "3fa84fd"
SRC_URI[sha256sum] = "154b1b5890ddebe45ca280634260a8cdaf38adc5b303aeea28a5ebad504a7912"

S = "${UNPACKDIR}/firmware-imx-${PV}-${IMX_SRCREV_ABBREV}"

inherit fsl-eula-unpack

# 3. Add the robust copy function to ensure EULA is found in S
do_copy_eula() {
    # Find EULA anywhere in WORKDIR (covers both 'sources/' and root WORKDIR cases)
    EULA_PATH=$(find ${UNPACKDIR} -type f -name EULA | head -n 1)

    if [ -n "$EULA_PATH" ]; then
        cp "$EULA_PATH" "${S}/EULA"
    else
        bbfatal "Could not find EULA file in ${UNPACKDIR}"
    fi
}

do_unpack[postfuncs] += "do_copy_eula"
