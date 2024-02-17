# Copyright (C) 2017-2021 NXP

require optee-test-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-test.git;protocol=https;branch=${SRCBRANCH}"

SRCBRANCH = "lf-6.1.55_2.2.0"
SRCREV = "38efacef3b14b32a6792ceaebe211b5718536fbb"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
