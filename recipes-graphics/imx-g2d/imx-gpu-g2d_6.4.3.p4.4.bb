# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2022 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "G2D library using i.MX GPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"
DEPENDS = "libgal-imx"
PROVIDES = "virtual/libg2d"

FSLBIN_NAME     = "${PN}-${PV}-${TARGET_ARCH}"

SRC_URI = "${FSL_MIRROR}/${FSLBIN_NAME}.bin;name=${TARGET_ARCH};fsl-eula=true"
SRC_URI[aarch64.md5sum] = "98d46bca2ae66e31b7e4878a7b622b99"
SRC_URI[aarch64.sha256sum] = "8341d6e2cde3f3ffa035dc95b0487dd04a50531cc80f4cddc5a689ebc4946e02"
SRC_URI[arm.md5sum] = "ff96d069092424a50ba4542d6ef5e931"
SRC_URI[arm.sha256sum] = "7737a3372bcde34e2d275397df67c307df5033d5b133ac770f75cac4e1ec2580"

S = "${WORKDIR}/${FSLBIN_NAME}"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp -r ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
}

INSANE_SKIP:${PN} = "ldflags"

COMPATIBLE_MACHINE = "(imxgpu2d)"
