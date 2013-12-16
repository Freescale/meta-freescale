# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require libfslcodec.inc

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "59c81f7da3440409e7e058edd0cdbb2a"
SRC_URI[sha256sum] = "130f69e47b52a02988df64211c2e42c54f0d07f00d8c1229d20d41b2a9d784a2"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
