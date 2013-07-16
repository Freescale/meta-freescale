# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require libfslcodec.inc

# FIXME: Inspecting the source code the content is in fact 3.0.8
SRC_URI = "${FSL_MIRROR}/${PN}-3.5.7-1.0.0.bin;fsl-eula=true"
S = "${WORKDIR}/${PN}-3.5.7-1.0.0"

SRC_URI[md5sum] = "bfdae0e2c6f7355c370c9bb6cd3ab9f9"
SRC_URI[sha256sum] = "657a1a67e26889efcecd8272a4420d88c0fc2a103d6551d0f126d8fe8c333a39"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
