# Copyright (C) 2017-2021 NXP

require optee-test-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH = "lf-6.1.36_2.1.0"
SRCREV = "e0ebd5193070e0215b5389da191bc33f4f478222"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
