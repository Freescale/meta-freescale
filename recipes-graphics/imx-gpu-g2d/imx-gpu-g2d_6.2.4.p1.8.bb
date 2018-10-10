# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU G2D library and apps for i.MX with 2D GPU and no DPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ab1a30d0cd181e3408077727ea5a2db"

DEPENDS += "libgal-imx"
PROVIDES += "virtual/libg2d"

# FIXME: arm packages are mis-labeled with aarch32 suffix
FSLBIN_NAME     = "${PN}-${PV}-${TARGET_ARCH}"
FSLBIN_NAME_arm = "${PN}-${PV}-aarch32"

SRC_URI = "${FSL_MIRROR}/${FSLBIN_NAME}.bin;name=${TARGET_ARCH};fsl-eula=true"
SRC_URI[arm.md5sum] = "ab7e43c997d3a84764fc3cc72d3b397c"
SRC_URI[arm.sha256sum] = "88b3af3dd109e7c9c457d19441dbb668e7c395d29340d724db3cc42dc04ae87b"
SRC_URI[aarch64.md5sum] = "287f2de4bd407b2eb484d332ae6de157"
SRC_URI[aarch64.sha256sum] = "77f191135f2be6052eabc8cca50b4ea7b5eaec015488cc108fb3f9656a3a1367"

S = "${WORKDIR}/${FSLBIN_NAME}"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
    cp -r ${S}/gpu-demos/opt ${D}
}

FILES_${PN} = "${libdir}/libg2d* /opt"
FILES_${PN}-dev = "${includedir}"
INSANE_SKIP_${PN} = "ldflags"

RDEPENDS_${PN} = "libgal-imx"

COMPATIBLE_MACHINE = "(imxgpu2d)"
