# Copyright (C) 2013 Freescale Semiconductor
require imx-lib.inc

SRC_URI = "${FSL_MIRROR}/imx-lib-${PV}.tar.gz \
           file://obey-variables.patch"
SRC_URI[md5sum] = "a4e7447e35cfac0b966a51f001390d6e"
SRC_URI[sha256sum] = "3ed082005789db0c9c41e14073417319f4e23f5646a7d21f34840470f4328bd5"

COMPATIBLE_MACHINE = "(mx5)"
