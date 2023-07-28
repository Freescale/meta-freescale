# Copyright (C) 2017-2021 NXP

require optee-test-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH = "lf-6.1.22_2.0.0"
SRCREV = "c2c9f922044d2c8a7ab384812bb124c6da2b7888"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
