# Copyright (C) 2013, 2014 Freescale Semiconductor

include imx-lib.inc

PE = "1"

SRC_URI += "file://obey-variables.patch"
SRC_URI[md5sum] = "8e3346d5f33b5aa5f915a0dd8aa99cc9"
SRC_URI[sha256sum] = "f42605971977e5fe1ed9e7ce17ea3f97586a23fbc60fa0f679940d379c72303e"

COMPATIBLE_MACHINE = "(mx6)"
