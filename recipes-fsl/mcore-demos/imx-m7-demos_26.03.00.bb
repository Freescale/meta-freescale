# Copyright 2023-2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mn-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"
LIC_FILES_CHKSUM:mx8mnul-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

LIC_FILES_CHKSUM:mx8mp-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"
LIC_FILES_CHKSUM:mx8mpul-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

LIC_FILES_CHKSUM:mx95-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

SRC_URI[imx8mn.sha256sum] = "e02fd9d7beb72831e21481839fc58e023a5f5cad54665aeee98669a52da67e43"
SRC_URI[imx8mnddr3l.sha256sum] = "eb7f479bf69cfa73bc59ba4289b25bcb01cafeb379839dec7797de8830137586"

SRC_URI[imx8mp.sha256sum] = "2ead4fa253e61dab3af84407ff7e60f66f6ea5b33b1c5eee9d83fc757263ba00"

SRC_URI[imx95.sha256sum] = "84c2ad9c858f321488dbc6f7d4c6a111e00053bc16ec1e659593ec694c73ad10"

COMPATIBLE_MACHINE = "(mx8mn-nxp-bsp|mx8mnul-nxp-bsp|mx8mp-nxp-bsp|mx8mpul-nxp-bsp|mx95-nxp-bsp)"
