# Copyright 2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mn-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac" 
LIC_FILES_CHKSUM:mx8mnul-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac" 

LIC_FILES_CHKSUM:mx8mp-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac" 
LIC_FILES_CHKSUM:mx8mpul-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

LIC_FILES_CHKSUM:mx95-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac" 

LIC_FILES_CHKSUM:mx952-nxp-bsp = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac" 

SRC_URI[imx8mn.sha256sum] = "fd4350c9ceceda29d6f7483f83a946b1876b364a4d9c8d9a9e6b618415d27b8e"
SRC_URI[imx8mnddr3l.sha256sum] = "cdaad7f840eec638568bde934408044a386e56b85d22a20c1de7faf77b0674c7"

SRC_URI[imx8mp.sha256sum] = "0413bdd2e94cc4ddbd71de4da4e50f9a07a4a29290dc73c53cab241bb50f51af"

SRC_URI[imx95.sha256sum] = "6894b63683a69be1f97e30c4ecce453c0aeaeb12e9078f83fb77a24334592c79"

SRC_URI[imx952.sha256sum] = "53e88afdea5b14f52ef6c69ffd6b2590c603554e239f691b701363ed8c09507a"

COMPATIBLE_MACHINE = "(mx8mn-nxp-bsp|mx8mnul-nxp-bsp|mx8mp-nxp-bsp|mx8mpul-nxp-bsp|mx95-nxp-bsp|mx952-nxp-bsp)"
