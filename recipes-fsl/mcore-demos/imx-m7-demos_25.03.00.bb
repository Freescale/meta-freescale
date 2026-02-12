# Copyright 2023-2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mn-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"
LIC_FILES_CHKSUM:mx8mnul-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

LIC_FILES_CHKSUM:mx8mp-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"
LIC_FILES_CHKSUM:mx8mpul-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

SRC_URI[imx8mn.sha256sum] = "6fb7bfad067f0dda55ce7d6e1f688da8d979e3a75a0a3e1325ad10684ce943b3"
SRC_URI[imx8mnddr3l.sha256sum] = "e107ca6f12e863368133a881d8b848813bdad77249777926880dffbebd3c30c9"

SRC_URI[imx8mp.sha256sum] = "d97c215a466bc4155ca96b6c6697f1512d6650c29241559ffefa52de0eaf8535"

COMPATIBLE_MACHINE = "(mx8mn-nxp-bsp|mx8mnul-nxp-bsp|mx8mp-nxp-bsp|mx8mpul-nxp-bsp)"

