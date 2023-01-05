# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2022 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "G2D library using i.MX DPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

DEPENDS = "libgal-imx libdrm"
PROVIDES += "virtual/libg2d"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "488d9f63b2d02c687a0bc8c3debfb51c"
SRC_URI[sha256sum] = "62050bc5dd35e942c97e7a8ab22b0b98d02e27a38935b585e622cdc5221ef2a3"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp -r ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
}

INSANE_SKIP:${PN} += "ldflags"
INSANE_SKIP:append:libc-musl = " file-rdeps"

# The packaged binaries have been stripped of debug info, so disable
# operations accordingly.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

COMPATIBLE_MACHINE = "(imxdpu)"

RDEPENDS:${PN}:append:libc-musl = " gcompat"
