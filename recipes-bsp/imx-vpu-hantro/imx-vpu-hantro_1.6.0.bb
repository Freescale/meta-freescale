# Copyright 2017 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=08fd295cce89b0a9c74b9b83ed74f671"

DEPENDS = "virtual/kernel"

PROVIDES = "virtual/imxvpu"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
           file://0001-Fix-ion.h-header-inclusion-to-be-standard.patch \
"

SRC_URI[md5sum] = "09ec833586afb5f194ce0202da925ed6"
SRC_URI[sha256sum] = "cbc648e41f005aad209f74c9e5dd346138dca12efeb7b27e471de7474c4da302"

inherit fsl-eula-unpack

PARALLEL_MAKE="-j 1"

do_compile () {
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" LINUX_KERNEL_ROOT="${STAGING_KERNEL_DIR}" SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" all
}

do_install () {
    oe_runmake DEST_DIR="${D}" install
}

FILES_${PN} += "/unit_tests"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8mq)"
