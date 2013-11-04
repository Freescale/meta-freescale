# Copyright (C) 2013 Freescale Semiconductor
DESCRIPTION = "Freescale VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://vpu/EULA.txt;md5=b063366b066c9f10037c59756a9ced54"
DEPENDS = "virtual/kernel"
inherit fsl-eula-unpack

PLATFORM_mx5 = "IMX51"
PLATFORM_mx6 = "IMX6Q"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

do_compile () {
    INCLUDE_DIR="-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include"

    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake PLATFORM="${PLATFORM}" DEST_DIR="${D}" install
}

