# Copyright 2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

SRC_URI[imx7ulp.sha256sum] = "1001a420db8627e0352e45cbd6bce9431232d446fa1597c43084a4ec813e7003"

SRC_URI[imx8mm.sha256sum] = "05e75ead477b2f73199161789c9935c114d039bd160097f3f24432b6064860d7"
SRC_URI[imx8mq.sha256sum] = "490a42fe91ca25145b93c5a2f4b98c5c9b41cbb21a68594e31de44194b9acf9d"

COMPATIBLE_MACHINE = "(mx7ulp-nxp-bsp|mx8mm-nxp-bsp|mx8mq-nxp-bsp)"
