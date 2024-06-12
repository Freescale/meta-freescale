# Copyright (C) 2017-2020 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=44a8052c384584ba09077e85a3d1654f"

PROVIDES = "virtual/imxvpu"

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "f751ab7369d48e610ea3b6b0dc5a885c70a510861d6b46296ffc063fed370003"

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
