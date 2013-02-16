# Copyright (C) 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "MIT"
SECTION = "x11/base"
DEPENDS = "virtual/libx11 util-macros xf86-video-imxfb-vivante"
LIC_FILES_CHKSUM = "file://src/dri.h;enline=27;md5=79a9064e49ae41adca4827cd2281a014"

SRC_URI = "${FSL_MIRROR}/dri-xorg-graphic-imx-viv-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "8c90045cd5f4dba81095856634ba5136"
SRC_URI[sha256sum] = "c844dc180e43901359bbdb4f797ab178b3821fbf63bdee9577e5a0afe5d7f6ad"

S = "${WORKDIR}/dri-xorg-graphic-imx-viv-${PV}"

PR = "r2"

inherit fsl-eula-unpack autotools pkgconfig

EXTRA_OECONF_armv7a = " --enable-neon "
CFLAGS += " -I${STAGING_INCDIR}/xorg"

do_install_append () {
# Install header files
    install -d ${D}${includedir}/xorg
    cp -axr ${S}/src/*.h ${D}${includedir}/xorg
    find ${D}${includedir} -type f -exec chmod 660 {} \;
# don't install libtool (*.la) archive not usefull, fix Makefile.am
    find ${D}${libdir}/xorg/modules -regex ".*\.la$" | xargs rm -f --
}

FILES_${PN}-dev += "${includedir}/xorg/*.h"
FILES_${PN} += " ${libdir}/xorg/modules/extensions/*.so"
FILES_${PN}-dbg += " ${libdir}/xorg/modules/extensions/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
