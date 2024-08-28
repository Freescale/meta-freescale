# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2024 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "G2D library using i.MX GPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=10c0fda810c63b052409b15a5445671a" 
DEPENDS = "libgal-imx"
PROVIDES = "virtual/libg2d"

SRC_URI = "${FSL_MIRROR}/${IMX_BIN_NAME}.bin;name=${TARGET_ARCH};fsl-eula=true"
IMX_BIN_NAME = "${BP}-${TARGET_ARCH}-${IMX_SRCREV_ABBREV}"
IMX_SRCREV_ABBREV = "bc7b6a2"
SRC_URI[aarch64.md5sum] = "4b12d0434d612b57ff4d7df690c3154f"
SRC_URI[aarch64.sha256sum] = "b2e4c580bf887042f479fde7c8b07c6215794d8123263d10ff0c167af7ba5918"
SRC_URI[arm.md5sum] = "4bead15838f47793f59cb374c10c2dd2"
SRC_URI[arm.sha256sum] = "413610ce4730eb9adbde30c8ad4a72df3edd69d84e1154a0c29efa7f33a30663"

S = "${WORKDIR}/${IMX_BIN_NAME}"

inherit fsl-eula-unpack

SOC_INSTALL_DIR               = "SOC_INSTALL_DIR_NOT_SET"
SOC_INSTALL_DIR:mx8mm-nxp-bsp = "mx8mm"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp -d ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    if [ -d ${S}/g2d/usr/lib/${SOC_INSTALL_DIR} ]; then
        cp -d ${S}/g2d/usr/lib/${SOC_INSTALL_DIR}/*.so* ${D}${libdir}
    fi
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
}

# The packaged binaries have been stripped of debug info, so disable
# operations accordingly.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imxgpu2d)"
