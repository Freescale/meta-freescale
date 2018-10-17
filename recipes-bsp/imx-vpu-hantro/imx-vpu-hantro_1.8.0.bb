# Copyright 2017-2018 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ab1a30d0cd181e3408077727ea5a2db"

PROVIDES = "virtual/imxvpu"

SRC_URI = " \
    ${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
    file://0001-Get-i.MX-custom-headers-from-usr-include-imx.patch \
"
SRC_URI[md5sum] = "140796ddd6f1be47cffb7e5e2bfe0fb6"
SRC_URI[sha256sum] = "c092a5b0f8897bae54154f58e47b6d2de033da01ee231a8cd779a51bbe962606"

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
