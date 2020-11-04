# Copyright (C) 2019-2020 NXP

DESCRIPTION = "i.MX VC8000E Encoder library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=983e4c77621568488dd902b27e0c2143"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

S = "${WORKDIR}/${BPN}-${PV}"

SRC_URI[md5sum] = "d2b7c0cfdb380e5a65a94251c2437a34"
SRC_URI[sha256sum] = "10a7f60964feb9641291815876fb8110dfec603c6451ed9b9c7fe57c23afd10e"

COMPATIBLE_MACHINE = "(mx8mp)"
