# Copyright (C) 2013-2018 O.S. Systems Software LTDA.
# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP

DESCRIPTION = "Freescale Chips&Media VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=6c12031a11b81db21cdfe0be88cac4b3"

PROVIDES = "virtual/imxvpu"

PE = "1"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "fb11619e07dafbc46a335c7db4c9b9a2"
SRC_URI[sha256sum] = "604885463ca7d292ddf376795ee95ae32abcc60512a3378e016b173222362900"

inherit fsl-eula-unpack use-imx-headers

PLATFORM_mx6 = "IMX6Q"

do_compile () {
    INCLUDE_DIR="-I${STAGING_INCDIR_IMX}"
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake PLATFORM="${PLATFORM}" DEST_DIR="${D}" install
}

COMPATIBLE_MACHINE = "(mx6q|mx6dl)"
