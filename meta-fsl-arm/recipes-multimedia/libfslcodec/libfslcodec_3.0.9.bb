# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require libfslcodec.inc

# FIXME: Inspecting the source code the content is in fact 3.0.9
SRC_URI = "${FSL_MIRROR}/${PN}-3.10.9-1.0.0.bin;fsl-eula=true"
S = "${WORKDIR}/${PN}-3.10.9-1.0.0"

SRC_URI[md5sum] = "8fd6f1f4f7a504adfc27c1f73de554b4"
SRC_URI[sha256sum] = "76a8a93d452db07e2531fbbc6dc1cbdf21943e6b3dfa1b2435faf5b356819f95"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
