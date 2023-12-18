# Copyright 2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx93-nxp-bsp = "file://COPYING;md5=db4762b09b6bda63da103963e6e081de"
LIC_FILES_CHKSUM:mx8ulp-nxp-bsp = "file://COPYING;md5=db4762b09b6bda63da103963e6e081de"

SRC_URI[imx8ulp.md5sum] = "6a1d2b904255ad2b514a234e6282209f"
SRC_URI[imx8ulp.sha256sum] = "eb8710c31dd58e23ed50653a1b90106e78776a1062abea2a16139cf371828a5a"

SRC_URI[imx93.md5sum] = "6c48e178dd3ddb95151f1eeade984a21"
SRC_URI[imx93.sha256sum] = "0848b5055ca693badd5ee923347fcebcac04e8aade22a4f26441669ef653eb01"

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp|mx93-nxp-bsp)"
