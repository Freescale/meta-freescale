# Copyright 2021 NXP
DESCRIPTION = "i.MX Hantro V4L2 Daemon"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cd8bc2a79509c22fc9c1782a151210b1"

DEPENDS = "imx-vpu-hantro"
DEPENDS:append:mx8mp = " imx-vpu-hantro-vc"

SRC_URI = " \
    ${FSL_MIRROR}/${BP}.tar.gz \
"
SRC_URI[md5sum] = "980237589af195c2c4b852a7aad175ee"
SRC_URI[sha256sum] = "a5d915fefd3637e68d693638edd1161c30e75011a6312251b914f04546fd0fc9"

PLATFORM:mx8mm = "IMX8MM"
PLATFORM:mx8mq = "IMX8MQ"
PLATFORM:mx8mp = "IMX8MP"

EXTRA_OEMAKE = " \
    CROSS_COMPILE="${HOST_PREFIX}" \
    SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" \
    CTRLSW_HDRPATH="${STAGING_INCDIR}" \
    PLATFORM="${PLATFORM}" \
"

do_install () {
    oe_runmake install DEST_DIR="${D}"
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx8mq|mx8mm|mx8mp)"
