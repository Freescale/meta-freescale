# Copyright 2017-2024 NXP

require optee-test-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH = "lf-6.6.36_2.1.0"
SRCREV = "5b52b48a73b4cc3f228ec66ae6cf9920897bb2e6"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
