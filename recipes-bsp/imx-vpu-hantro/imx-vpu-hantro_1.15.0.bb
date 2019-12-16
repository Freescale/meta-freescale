# Copyright 2017-2018 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=6c12031a11b81db21cdfe0be88cac4b3"

PROVIDES = "virtual/imxvpu"

SRC_URI = " \
    ${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true \
"
SRC_URI[md5sum] = "2cb9a95223bee98a5b3d86c89ee36a55"
SRC_URI[sha256sum] = "8d189156d713f12b255f16badd0093aaacd16859aa3191603908de07567485a5"

inherit fsl-eula-unpack use-imx-headers

PARALLEL_MAKE="-j 1"

PLATFORM_mx8mm = "IMX8MM"
PLATFORM_mx8mq = "IMX8MQ"

do_compile () {
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" \
        SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" \
        PLATFORM="${PLATFORM}" all
}

do_install () {
    oe_runmake DEST_DIR="${D}" PLATFORM="${PLATFORM}" install
}

FILES_${PN} += "/unit_tests"

COMPATIBLE_MACHINE = "(mx8m)"
