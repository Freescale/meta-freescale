# Copyright 2017-2024 NXP

require optee-test-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH = "lf-6.6.23_2.0.0"
SRCREV = "07682f1b1b41ec0bfa507286979b36ab8d344a96"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
