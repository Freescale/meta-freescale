# Copyright 2023-2024 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8ulp-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"
LIC_FILES_CHKSUM:mx93-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"

SRC_URI[imx8ulp.sha256sum] = "2d1d48eb3f01d020917f42281581835a3c085233e9764ec845940c1e49ac317b"

SRC_URI[imx93.sha256sum] = "c954b9bc32cfdea9b696fa0b02f934812054e3ab445b5dd8eb7898d427e47526"

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp|mx93-nxp-bsp)"
