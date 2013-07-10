# Copyright (C) 2011-2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-imx.inc

PR = "${INC_PR}.14"

COMPATIBLE_MACHINE = "(mx6)"

# Revision of 4.0.0 branch
SRCREV = "08814d923d74509cfaab96c1eb121f4bf961d942"
LOCALVERSION = "-4.0.0+yocto"
SRC_URI += "file://arm-mach-mx6-fix-pll4-set_rate-callback.patch"
