# Copyright (C) 2011 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "X.Org X server -- Freescale iMx framebuffer driver"
LICENSE = "MIT-X"
RDEPENDS = "amd-gpu-x11-bin-mx51"
DEPENDS = "virtual/xserver-xf86 virtual/libx11 xproto randrproto util-macros amd-gpu-x11-bin-mx51"
LIC_FILES_CHKSUM = "file://COPYING;md5=f7bdc0c63080175d1667091b864cb12c"

inherit autotools pkgconfig

PR = "r0"
SRC_URI = "http://opensource.freescale.com/pub/scm/imx/xserver-xorg-video-imx-${PV}.tar.gz \
           file://xf86-video-imxfb-fix-m4-hardcodded-paths.patch"

FILES_${PN} += " ${libdir}/xorg/modules/drivers/*.so"
FILES_${PN}-dbg += " ${libdir}/xorg/modules/drivers/.debug"
EXTRA_OECONF_armv7a = " --enable-neon "
CFLAGS += " -I${STAGING_INCDIR}/xorg "

S = "${WORKDIR}/xserver-xorg-video-imx-${REL}"

do_install_append () {
    # driver's la files are not packaged
    rm -f ${D}${libdir}/xorg/modules/drivers/*.la
}
