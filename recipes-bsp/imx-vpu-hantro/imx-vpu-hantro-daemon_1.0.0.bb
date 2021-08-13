# Copyright 2021 NXP
DESCRIPTION = "i.MX Hantro V4L2 Daemon"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cd8bc2a79509c22fc9c1782a151210b1"

DEPENDS = "imx-vpu-hantro"
DEPENDS:append:mx8mp = " imx-vpu-hantro-vc"

SRC_URI = " \
    ${FSL_MIRROR}/${BP}.tar.gz \
    file://0001-Makefile-Honor-LDFLAGS-from-toolchain.patch \
"
SRC_URI[md5sum] = "65417710ef22214523c37f50f34b6ab2"
SRC_URI[sha256sum] = "897d174c4c0818f31012d8a60ed51fc83fdfc55fca2d2b0fc24a0db3e9f3b6e8"

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
