# Copyright (C) 2012-2013 Freescale Semiconductor
# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/xorg-driver/xorg-driver-video.inc

PE = "3"
PR = "${INC_PR}.3"

DEPENDS += "virtual/xserver virtual/libx11 virtual/libgal-x11 gpu-viv-bin-mx6q"

LIC_FILES_CHKSUM = "file://src/vivante_fbdev/vivante.h;endline=19;md5=93a322f91ec495569dcbcfbb2a95454a"

SRC_URI = "${FSL_MIRROR}/xserver-xorg-video-imx-viv-${PV}.tar.gz \
           file://Makefile.am-remove-prefixed-include-path.patch"
SRC_URI[md5sum] = "d872365c046738628a7016343ffdb79a"
SRC_URI[sha256sum] = "d53216d5f9e3f7803983ac1577d83985dfda33145e4711300f4ad5cbbe28e32d"

EXTRA_OECONF_armv7a = " --enable-neon --disable-static"
CFLAGS += " -I${STAGING_INCDIR}/xorg -I${STAGING_INCDIR}/drm"
LDFLAGS += " -lGAL"

S = "${WORKDIR}/xserver-xorg-video-imx-viv-${PV}/EXA/"

do_compile_prepend () {
    # FIXME: Allow build without depending on xserver-xorg
    #        as this is the is the only source dependency and
    #        by default Xorg does not install this header anyway.
    cp ${S}/../DRI*/src/dri.h ${S}/../DRI*/src/sarea.h ${S}/src/vivante_fbdev/
}

do_install_append () {
	install -d ${D}${includedir}
	cp -axr ${S}/src/vivante_gal/vivante_priv.h ${D}${includedir}
	cp -axr ${S}/src/vivante_gal/vivante_gal.h ${D}${includedir}
	find ${D}${includedir} -type f -exec chmod 660 {} \;
}

RDEPENDS_${PN} += "libvivante-dri-mx6 \
                   xserver-xorg-module-exa \
                   mesa-driver-swrast \
                   xf86-dri-vivante"

# Add the ABI dependency at package generation time, as otherwise bitbake will
# attempt to find a provider for it (and fail) when it does the parse.
#
# This version *must* be kept correct.
python populate_packages_prepend() {
    pn = d.getVar("PN", True)
    d.appendVar("RDEPENDS_" + pn, " xorg-abi-video-11")
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
