# Copyright 2017-2018 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=80c0478f4339af024519b3723023fe28"

PROVIDES = "virtual/imxvpu"

SRC_URI = " \
    ${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true \
"
SRC_URI[md5sum] = "51b811f9523d03ca87f611ba86594311"
SRC_URI[sha256sum] = "cec620241d303852ddad8601f2025a68abbc1ec26d605103c7e4173749b59e3f"

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
