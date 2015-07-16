# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-gpu-viv.inc

SRC_URI = "${FSL_MIRROR}/${PN}-${@'${PV}'.replace('5.0.11.p6.3', '5.0.11.p6.3-beta')}.bin;fsl-eula=true"

SRC_URI[md5sum] = "ba6b5dfd10f8d0bf8533e8ba09a741b8"
SRC_URI[sha256sum] = "2bf62e24ca677159534b503ce247c1a8a50198db9050de1f8fe44fde7a1c786d"

PACKAGE_FP_TYPE = "hardfp"
