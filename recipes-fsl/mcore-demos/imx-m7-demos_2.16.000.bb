# Copyright 2023-2024 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mn-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"
LIC_FILES_CHKSUM:mx8mnul-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"

LIC_FILES_CHKSUM:mx8mp-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"
LIC_FILES_CHKSUM:mx8mpul-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"

LIC_FILES_CHKSUM:mx95-nxp-bsp = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a"

SRC_URI[imx8mn.sha256sum] = "ab608d621c8d74f42514799a732414184bd22f058a8a87a0ab75147a0fd5d99c"

SRC_URI[imx8mnddr3l.sha256sum] = "9c9477db2581640395ec3bc7524f89c2c09ffd2947d9186959343a8cf764ad28"

SRC_URI[imx8mp.sha256sum] = "672af1e1d8fcc1ebd12b45c6dc4cfc7abc85ea9ebbf114d15f4bfe7540e24ea1"

SRC_URI[imx95.sha256sum] = "8d394b62a4fbad8303acf43f9f8672e5d1c17f1af8f47d7665740272819c0cc1"

COMPATIBLE_MACHINE = "(mx8mn-nxp-bsp|mx8mnul-nxp-bsp|mx8mp-nxp-bsp|mx8mpul-nxp-bsp|mx95-nxp-bsp)"
