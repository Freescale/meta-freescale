# Copyright 2017-2024 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx7ulp-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"
LIC_FILES_CHKSUM:mx8mm-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"
LIC_FILES_CHKSUM:mx8mq-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"

SRC_URI[imx7ulp.sha256sum] = "1fbb26f8e0a69a2e3925e07cf0cc4a13b13e3ff6321a2ba3dc39c454c82b398b"

SRC_URI[imx8mm.sha256sum] = "a335b54b797c37be05c5b4e6aeabd02099f401de907186723361d1fc25ea3f24"

SRC_URI[imx8mq.sha256sum] = "09b7195ce553dae5808afcefaf6f76c79f7122ce4dd31fabd679b768607dea25"

COMPATIBLE_MACHINE = "(mx7ulp-nxp-bsp|mx8mm-nxp-bsp|mx8mq-nxp-bsp)"
