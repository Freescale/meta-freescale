# Copyright (C) 2012-2014 Freescale Semiconductor
# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/xorg-driver/xorg-driver-video.inc

PE = "3"

inherit autotools-brokensep

DEPENDS += "virtual/xserver virtual/libx11 virtual/libgal-x11 gpu-viv-bin-mx6q pixman"

LIC_FILES_CHKSUM = "file://EXA/src/vivante_fbdev/vivante.h;endline=19;md5=95cf961a2ceacdf7cf43caef25766779"

SRC_URI = "${FSL_MIRROR}/xserver-xorg-video-imx-viv-${PV}-beta.tar.gz \
           file://ENGR00333447-Fix-EXA-driver-for-Xserver-1.16.patch"
SRC_URI[md5sum] = "5695e9a3ddc37da0e783862e4108fbae"
SRC_URI[sha256sum] = "c08def7fb98c11cba08bc21522bfb2785576b77db4b36954b15926401ac31c0e"

EXTRA_OEMAKE += "-C EXA/src -f makefile.linux prefix=${D}/usr \
                 sysroot=${STAGING_DIR_TARGET} \
                 BUSID_HAS_NUMBER=1 \
                 BUILD_IN_YOCTO=1 \
                 XSERVER_GREATER_THAN_13=1"

CFLAGS += "-I${STAGING_INCDIR}/xorg \
           -I${STAGING_INCDIR}/drm \
           -I../../DRI_1.10.4/src"

S = "${WORKDIR}/xserver-xorg-video-imx-viv-${PV}-beta/"

# FIXME: The Freescale provided Makefile has hardcodec include paths
#        and this does not work in case prefix is different than /usr,
#        sed it.
do_configure_prepend () {
    sed -i 's,$(sysroot)/usr/include,${STAGING_INCDIR},g' \
            ${S}EXA/src/makefile.linux
}

# FIXME: This is need as Freescale didn't use standard Makefile filename
#        thus oe_runmame thinks nothing is need to be done, use ln to
#        workaround it.
base_do_compile () {
    oe_runmake || die "make failed"
}

do_install_append () {
	install -d ${D}${includedir}
	cp -axr ${S}/EXA/src/vivante_gal/vivante_priv.h ${D}${includedir}
	cp -axr ${S}/EXA/src/vivante_gal/vivante_gal.h ${D}${includedir}
	find ${D}${includedir} -type f -exec chmod 660 {} \;
}

RDEPENDS_${PN} += "libvivante-dri-mx6 \
                   xserver-xorg-module-exa \
                   mesa-driver-swrast \
                   xserver-xorg-extension-dri \
                   xserver-xorg-extension-dri2 \
                   xserver-xorg-extension-glx"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx6)"
