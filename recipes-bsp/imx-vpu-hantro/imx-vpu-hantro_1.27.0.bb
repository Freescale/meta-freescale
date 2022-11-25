# Copyright (C) 2017-2020 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

PROVIDES = "virtual/imxvpu"

SRC_URI = " \
    ${FSL_MIRROR}/${BP}.bin;fsl-eula=true \
    file://0001-buildsystem-don-t-ignore-externally-set-cflags.patch \
"
SRC_URI[md5sum] = "cfa0e92da0eca9cf622c4d7abc618cef"
SRC_URI[sha256sum] = "7e9bcdbd067ce66a1247373cbf088945346feb0027deb61af541ebc1256277c3"

inherit fsl-eula-unpack use-imx-headers

PARALLEL_MAKE="-j 1"

PLATFORM:mx8mm-nxp-bsp = "IMX8MM"
PLATFORM:mx8mq-nxp-bsp = "IMX8MQ"
PLATFORM:mx8mp-nxp-bsp = "IMX8MP"

EXTRA_OEMAKE = " \
    CROSS_COMPILE="${HOST_PREFIX}" \
    SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" \
    PLATFORM="${PLATFORM}" \
"

do_install () {
    oe_runmake install DEST_DIR="${D}"
}

FILES:${PN} += "/unit_tests"

RDEPENDS:${PN} += "imx-vpu-hantro-daemon"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx8mq-nxp-bsp|mx8mm-nxp-bsp|mx8mp-nxp-bsp)"
