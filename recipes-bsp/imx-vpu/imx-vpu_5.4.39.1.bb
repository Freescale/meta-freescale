# Copyright (C) 2013-2018 O.S. Systems Software LTDA.
# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP

DESCRIPTION = "Freescale Chips&Media VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=6c12031a11b81db21cdfe0be88cac4b3"

PROVIDES = "virtual/imxvpu"

PE = "1"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "9f6136a1300d322078daaa9676054deb"
SRC_URI[sha256sum] = "b7bb67b00daf436fd91a2760c2db375a962e2c919045ce8bf81bd54b95d999c5"

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
