# Copyright 2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx7ulp-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

LIC_FILES_CHKSUM:mx8mm-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"
LIC_FILES_CHKSUM:mx8mq-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

SRC_URI[imx7ulp.sha256sum] = "69ffe1fff83b099122811af60741d4800411d40645966ba40d3280b8621aa5f3"

SRC_URI[imx8mm.sha256sum] = "26719ec1fb61eecf904cfdcf07ad3f94f681bd106508607d324f7c541c079574"
SRC_URI[imx8mq.sha256sum] = "1550892e0d59469aac8d9fa55ff28a7488de14e90a603dfddc7f12249af4238d"

COMPATIBLE_MACHINE = "(mx7ulp-nxp-bsp|mx8mm-nxp-bsp|mx8mq-nxp-bsp)"
