# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2021 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "G2D library using i.MX GPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=03bcadc8dc0a788f66ca9e2b89f56c6f"

DEPENDS = "libgal-imx"
PROVIDES = "virtual/libg2d"

FSLBIN_NAME     = "${PN}-${PV}-${TARGET_ARCH}"

SRC_URI = "${FSL_MIRROR}/${FSLBIN_NAME}.bin;name=${TARGET_ARCH};fsl-eula=true"
SRC_URI[aarch64.md5sum] = "9d64a9c4a870419607d9e47e832eb997"
SRC_URI[aarch64.sha256sum] = "6065f545568e6cdc138f2d6e7cdcb525e477ce1d775c0ceabe2beb2eacf4eb5b"
SRC_URI[arm.md5sum] = "4d6cb47f09f268533cc6a9a90cc87555"
SRC_URI[arm.sha256sum] = "b298a38a16400e655ea46ab25b6261e602687e9970dd755594db343064a9ae53"

S = "${WORKDIR}/${FSLBIN_NAME}"

inherit fsl-eula-unpack

do_install () {
    cp -r -d --no-preserve=ownership ${S}/g2d/* ${D}
}

INSANE_SKIP:${PN} = "ldflags"

COMPATIBLE_MACHINE = "(imxgpu2d)"
