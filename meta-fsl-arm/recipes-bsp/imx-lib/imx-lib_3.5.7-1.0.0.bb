# Copyright (C) 2013 Freescale Semiconductor

require imx-lib.inc

PE = "1"

SRC_URI = "${FSL_MIRROR}/imx-lib-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "70248d0932a6d2808701166f41b1708f"
SRC_URI[sha256sum] = "ad746e34cf7d8cd9cb3f3f6c8eda85d9fa314fc6f57502d1f9cb454c5fab2f9d"

COMPATIBLE_MACHINE = "(mx6)"
