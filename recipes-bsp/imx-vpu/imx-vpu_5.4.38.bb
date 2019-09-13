# Copyright (C) 2013-2018 O.S. Systems Software LTDA.
# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP

DESCRIPTION = "Freescale Chips&Media VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=75abe2fa1d16ca79f87cde926f05f72d"

PROVIDES = "virtual/imxvpu"

PE = "1"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "521d5f839829f8d1f2a6a0e60270243e"
SRC_URI[sha256sum] = "34bed0ddf6c797f444bddfa5d5495adc751ff268b6431d8beb48129c89c9e47f"

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
