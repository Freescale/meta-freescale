# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP

include imx-test.inc

PV = "6.0+${SRCPV}"

PARALLEL_MAKE="-j 1"

SRCBRANCH = "imx_4.9.11_1.0.0_ga"
SRC_URI = "git://git.freescale.com/imx/imx-test.git;protocol=git;branch=${SRCBRANCH}"
SRCREV = "fb250a795ce0d25c19610e9e19e1cd815fc64cb9"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(mx6|mx7)"
