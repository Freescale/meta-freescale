# Copyright 2017-2024 NXP

require optee-client-fslc.inc

SRC_URI += "git://github.com/nxp-imx/imx-optee-client.git;protocol=https;branch=${SRCBRANCH}"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
