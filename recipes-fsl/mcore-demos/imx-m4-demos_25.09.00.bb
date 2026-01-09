# Copyright 2017-2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mm-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"
LIC_FILES_CHKSUM:mx8mq-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

SRC_URI[imx8mm.sha256sum] = "b2a08b5d5aeb23ffb9d30f915a551c891c997d6ed55f12a9e21103b6153752cc"
SRC_URI[imx8mq.sha256sum] = "9cd1e2e1f328911ea7fb13cd4da213a2ca0d08108963ca501c694edad100ec3f"

COMPATIBLE_MACHINE = "(mx8mm-nxp-bsp|mx8mq-nxp-bsp)"

