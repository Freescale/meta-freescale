# Copyright 2019-2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mn-nxp-bsp = "file://COPYING;md5=add2d392714d3096ed7e0f7e2190724b"
LIC_FILES_CHKSUM:mx8mnul-nxp-bsp = "file://COPYING;md5=add2d392714d3096ed7e0f7e2190724b"

SRC_URI[imx8mn.md5sum] = "c25e8463414f31f812e997748a5c3eba"
SRC_URI[imx8mn.sha256sum] = "8795f5947334eb2eae4a16c51eabd3cf50d8d04894b12933d8e78669433f7093"

SRC_URI[imx8mnddr3l.md5sum] = "f2e0741c26f59d2b7bf0b685d236ea6f"
SRC_URI[imx8mnddr3l.sha256sum] = "86039ee610ebb66cf1e65aa915cee2aeff5982f73504cebc9a94fba40095f3e1"

COMPATIBLE_MACHINE = "(mx8mn-nxp-bsp|mx8mnul-nxp-bsp)"
