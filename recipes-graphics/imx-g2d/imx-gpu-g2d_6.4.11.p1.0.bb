# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2022 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "G2D library using i.MX GPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ea25d099982d035af85d193c88a1b479"
DEPENDS = "libgal-imx"
PROVIDES = "virtual/libg2d"

SRC_URI = "${FSL_MIRROR}/${FSLBIN_NAME}.bin;name=${TARGET_ARCH};fsl-eula=true"
FSLBIN_NAME = "${PN}-${PV}-${TARGET_ARCH}"
SRC_URI[aarch64.md5sum] = "7aafa5910ef3e3be1c449b4bddd97b56"
SRC_URI[aarch64.sha256sum] = "9bf8ebbfc85245d75ab7d133c3e135a4c5f23a0430c75883af9700e9cb82c151"
SRC_URI[arm.md5sum] = "e0da61541b0b95083a3c95cc0bba4f63"
SRC_URI[arm.sha256sum] = "65427c12b43c7bb3838ec2bc91a5853c310652b920a65b2b52bacad8e761a8e4"

S = "${WORKDIR}/${FSLBIN_NAME}"

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
