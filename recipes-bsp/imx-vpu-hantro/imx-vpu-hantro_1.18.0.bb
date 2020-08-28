# Copyright (C) 2017-2020 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=228c72f2a91452b8a03c4cab30f30ef9"

PROVIDES = "virtual/imxvpu"

SRC_URI = " \
    ${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true \
    file://0001-decoder_sw-resolve-compilation-error-with-fcommon.patch \
"
SRC_URI[md5sum] = "78034de7ed74363eb793d29894bba5e3"
SRC_URI[sha256sum] = "bebd82649d00d6dd8236b77b8677b1cc6ac46dc474200502df7797a75dc8f568"

inherit fsl-eula-unpack use-imx-headers

PARALLEL_MAKE="-j 1"

PLATFORM_mx8mm = "IMX8MM"
PLATFORM_mx8mq = "IMX8MQ"
PLATFORM_mx8mp = "IMX8MP"

do_compile () {
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" \
        SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" \
        PLATFORM="${PLATFORM}" all
}

do_install () {
    oe_runmake DEST_DIR="${D}" PLATFORM="${PLATFORM}" install
}

FILES_${PN} += "/unit_tests"

COMPATIBLE_MACHINE = "(mx8mq|mx8mm|mx8mp)"
