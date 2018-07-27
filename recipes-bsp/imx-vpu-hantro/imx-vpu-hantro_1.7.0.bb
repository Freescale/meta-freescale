# Copyright 2017-2018 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab61cab9599935bfe9f700405ef00f28"

DEPENDS = "virtual/kernel"

PROVIDES = "virtual/imxvpu"

SRC_URI = " \
    ${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
    file://0001-Fix-ion.h-header-inclusion-to-be-standard.patch \
"
SRC_URI[md5sum] = "9a8ade25333e6ac3f7c345b71f3477a6"
SRC_URI[sha256sum] = "a3bbf2d8ac00ecae6d48b05cb94d9bdf68085d5bfc54eb176e3bf59670a87ad1"

inherit fsl-eula-unpack

PARALLEL_MAKE="-j 1"

PLATFORM_mx8mq = "IMX8MQ"

do_compile () {
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" LINUX_KERNEL_ROOT="${STAGING_KERNEL_DIR}" SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" PLATFORM="${PLATFORM}" all
}

do_install () {
    oe_runmake DEST_DIR="${D}" PLATFORM="${PLATFORM}" install
}

FILES_${PN} += "/unit_tests"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8mq)"
