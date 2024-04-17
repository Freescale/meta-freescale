# Copyright (C) 2017-2021 NXP

require optee-test-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH = "lf-6.6.3_1.0.0"
SRCREV = "95c49d950f50fa774e4530d19a967079b3b61279"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
