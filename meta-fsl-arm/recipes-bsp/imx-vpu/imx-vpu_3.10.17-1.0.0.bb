# Copyright (C) 2013, 2014 Freescale Semiconductor

require imx-vpu.inc

PE = "1"

# FIXME: Drop 'beta' suffix for GA release
SRC_URI = "${FSL_MIRROR}/${PN}-${PV}_beta.bin;fsl-eula=true"
S = "${WORKDIR}/${PN}-${PV}_beta"

SRC_URI[md5sum] = "7ba78483bd1cf61bb6fce4ed9e9287d6"
SRC_URI[sha256sum] = "589500bcc1c871d688ec23f11b930cd49079a689ed85d974ee8b32540c601cdd"

COMPATIBLE_MACHINE = "(mx6)"
