# Copyright 2023-2025 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mn-nxp-bsp = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"
LIC_FILES_CHKSUM:mx8mnul-nxp-bsp = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

LIC_FILES_CHKSUM:mx8mp-nxp-bsp = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"
LIC_FILES_CHKSUM:mx8mpul-nxp-bsp = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

SRC_URI[imx8mn.sha256sum] = "66e160a4267efcd2660b932d46ad6bcc645c9f2865c8ea873d4194d67fcf342c"

SRC_URI[imx8mnddr3l.sha256sum] = "45c770ed5539c5e0d5dd8aee2ad0a6ef91b28c99be3aaa9f336bda6ec34a7083"

SRC_URI[imx8mp.sha256sum] = "94b85ec4dcbf1664a8997e3cfb7bde6b7f1e5825482096302971a7da853b145f"

COMPATIBLE_MACHINE = "(mx8mn-nxp-bsp|mx8mnul-nxp-bsp|mx8mp-nxp-bsp|mx8mpul-nxp-bsp)"

