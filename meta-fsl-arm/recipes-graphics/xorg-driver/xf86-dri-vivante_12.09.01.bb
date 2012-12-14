# Copyright (C) 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "MIT"
SECTION = "x11/base"
DEPENDS = "virtual/libx11 util-macros xf86-video-imxfb-vivante"
LIC_FILES_CHKSUM = "file://src/dri.h;enline=27;md5=1d0d59e1dc96f5197ea3a8b101bf1fcc"

SRC_URI = "${FSL_MIRROR}/dri-xorg-graphic-imx-viv-${PV}.tar.gz"
SRC_URI[md5sum] = "6a3d8130945a24294d34b61cd59e5d59"
SRC_URI[sha256sum] = "437187360fe76fbee5ad70b1a661d3196568cc8c28a457170494effef859d054"

S = "${WORKDIR}/dri-xorg-graphic-imx-viv-${PV}"

PR = "r1"

inherit autotools pkgconfig

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
