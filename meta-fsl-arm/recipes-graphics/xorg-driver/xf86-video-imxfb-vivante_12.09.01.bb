# Copyright (C) 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/xorg-driver/xorg-driver-video.inc

PR = "${INC_PR}.1"

DEPENDS += "virtual/libx11 virtual/libgal-x11 gpu-viv-bin-mx6q"

LIC_FILES_CHKSUM = "file://src/vivante_fbdev/vivante.h;endline=19;md5=93a322f91ec495569dcbcfbb2a95454a"

SRC_URI = "${FSL_MIRROR}/xserver-xorg-video-imx-viv-${PV}.tar.gz \
           file://fix-vivante-compile.patch \
           file://Makefile.am-remove-prefixed-include-path.patch"
SRC_URI[md5sum] = "1948119717aa01bed1f630be9ee7a708"
SRC_URI[sha256sum] = "5b3be4b426d2d2803554df9e4d8919d1f9d17659c3153c71c6529f43c37e6ed1"

EXTRA_OECONF_armv7a = " --enable-neon --disable-static"
CFLAGS += " -I${STAGING_INCDIR}/xorg -I${STAGING_INCDIR}/drm"
LDFLAGS += "-lm -ldl -lX11 -lGAL-x11"

S = "${WORKDIR}/xserver-xorg-video-imx-viv-${PV}"

do_install_append () {
	install -d ${D}${includedir}
	cp -axr ${S}/src/vivante_gal/vivante_priv.h ${D}${includedir}
	find ${D}${includedir} -type f -exec chmod 660 {} \;
}

RDEPENDS_${PN} += "xserver-xorg-module-exa"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
