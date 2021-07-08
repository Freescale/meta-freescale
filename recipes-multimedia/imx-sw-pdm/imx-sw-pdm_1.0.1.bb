# Copyright 2020 NXP Semiconductors

DESCRIPTION = "NXP PDM to PCM Software Decimation SIMD Library"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=983e4c77621568488dd902b27e0c2143"

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[md5sum] = "65d5b2cb378f96a5cb4577925eda236b"
SRC_URI[sha256sum] = "68fca50d8f7526127ee5650e9002b3f9479b197f3085c68a97f7957f50ec5e5b"

inherit fsl-eula-unpack autotools pkgconfig

INSANE_SKIP_${PN} = "already-stripped"

FILES_${PN} += "${datadir}/imx-mm"

COMPATIBLE_MACHINE = "(mx8)"
