# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require xf86-video-imxfb-vivante.inc

SRC_URI += "file://Stop-using-Git-to-write-local-version.patch"

SRCBRANCH = "nxp/imx_4.9.11_1.0.0_ga"
SRCREV = "07ef065dfe09f1c05a1a188c371577faa3677a17"
