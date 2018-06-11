# Copyright (C) 2013-2018 O.S. Systems Software LTDA.
# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP

DESCRIPTION = "Freescale VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=75abe2fa1d16ca79f87cde926f05f72d"

DEPENDS = "virtual/kernel"

PROVIDES = "virtual/imxvpu"

PE = "1"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "521d5f839829f8d1f2a6a0e60270243e"
SRC_URI[sha256sum] = "34bed0ddf6c797f444bddfa5d5495adc751ff268b6431d8beb48129c89c9e47f"

inherit fsl-eula-unpack

PLATFORM_mx6 = "IMX6Q"

do_compile () {
    INCLUDE_DIR="-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include"

    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake PLATFORM="${PLATFORM}" DEST_DIR="${D}" install
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
