# Copyright (C) 2017-2021 NXP

require optee-test-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH = "lf-6.1.1_1.0.0"
SRCREV = "7c314e6a0cec0ba19246eb4f1959859d7a6536d6"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
