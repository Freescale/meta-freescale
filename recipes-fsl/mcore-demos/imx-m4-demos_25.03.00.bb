# Copyright 2017-2025 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mm-nxp-bsp = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"
LIC_FILES_CHKSUM:mx8mq-nxp-bsp = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

SRC_URI[imx8mm.sha256sum] = "30fcf9032a3bd71211b8df03c654acf98d5dd4631d18a34635d5b1ba61201d6c"
SRC_URI[imx8mq.sha256sum] = "652c0521587d9594d56115b549b23d75ea598237f36a79d5a7eede97d9f56574"

COMPATIBLE_MACHINE = "(mx8mm-nxp-bsp|mx8mq-nxp-bsp)"

