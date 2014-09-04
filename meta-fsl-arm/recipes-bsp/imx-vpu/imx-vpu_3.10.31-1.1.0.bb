# Copyright (C) 2013, 2014 Freescale Semiconductor

require imx-vpu.inc

PE = "1"

# FIXME: Drop 'beta' suffix for GA release
SRC_URI = "${FSL_MIRROR}/${PN}-${PV}-beta.bin;fsl-eula=true"
S = "${WORKDIR}/${PN}-${PV}-beta"

SRC_URI += "file://obey-variables.patch"

SRC_URI[md5sum] = "ab76e7395b6a178a8538a6d5beb87656"
SRC_URI[sha256sum] = "22d0542bd4d7beb88084575c834bf9045c8e2acc43acdd2ec4e0e5534b4b9c5e"

COMPATIBLE_MACHINE = "(mx6)"
