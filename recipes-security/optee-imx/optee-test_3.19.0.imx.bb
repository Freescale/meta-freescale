# Copyright (C) 2017-2021 NXP

require optee-test-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH = "lf-5.15.71_2.2.0"
SRCREV = "5c1dbb531b304f7ae100958f6261b6cefea49b62"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
