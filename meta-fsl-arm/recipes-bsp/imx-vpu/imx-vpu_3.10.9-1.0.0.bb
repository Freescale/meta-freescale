# Copyright (C) 2013 Freescale Semiconductor

require recipes-bsp/imx-lib/imx-lib.inc

inherit fsl-eula-unpack

DESCRIPTION = "Freescale VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://vpu/EULA.txt;md5=b063366b066c9f10037c59756a9ced54"

PE = "1"

SRC_URI = "${FSL_MIRROR}/imx-vpu-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "61331c9ed2d4c1b3aeab9c35fd034ac3"
SRC_URI[sha256sum] = "5dd86a26c8d3013e0d78308b1ccd8815730818e465787f55013b492d2d3c7710"

do_compile () {
    INCLUDE_DIR="-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include"

    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

COMPATIBLE_MACHINE = "(mx6)"
