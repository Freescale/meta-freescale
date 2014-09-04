# Copyright (C) 2012-2014 Freescale Semiconductor

require firmware-imx.inc

PE="1"

SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV}-beta.bin;fsl-eula=true"

S = "${WORKDIR}/firmware-imx-${PV}-beta"

SRC_URI[md5sum] = "4e102affc4273f424da4cec9f46adae8"
SRC_URI[sha256sum] = "8fcfc713321c4ffaafee248feb392b7bc9b913b7abee5020fceaca1d1a49ac99"

COMPATIBLE_MACHINE = "(mx5|mx6)"
