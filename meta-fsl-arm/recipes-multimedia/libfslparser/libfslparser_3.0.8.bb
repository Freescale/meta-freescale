# Copyright (C) 2012-2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require libfslparser.inc

# FIXME: Inspecting the source code the content is in fact 3.0.8
SRC_URI = "${FSL_MIRROR}/${PN}-3.5.7-1.0.0.bin;fsl-eula=true"
S = "${WORKDIR}/${PN}-3.5.7-1.0.0"

SRC_URI[md5sum] = "67e02486fba970a922f1e0ab686b9ab0"
SRC_URI[sha256sum] = "757640954637599159f7491942723a3bd59abd2474785f6ba31bbdd06d06fe45"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
