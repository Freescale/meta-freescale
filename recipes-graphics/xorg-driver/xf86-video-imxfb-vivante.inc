# Copyright (C) 2012-2015 Freescale Semiconductor
# Copyright (C) 2012-2016 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/xorg-driver/xorg-driver-video.inc

PE = "3"

inherit autotools-brokensep update-rc.d pkgconfig

DEPENDS += "virtual/xserver virtual/libx11 virtual/libgal-x11 imx-gpu-viv pixman"

LIC_FILES_CHKSUM = "file://COPYING-MIT;md5=b5e9d9f5c02ea831ab3ecf802bb7c4f3"

SRC_URI = "${FSL_MIRROR}/xserver-xorg-video-imx-viv-${PV}.tar.gz \
            file://rc.autohdmi"

INITSCRIPT_PACKAGES = "xserver-xorg-extension-viv-autohdmi"
INITSCRIPT_NAME = "rc.autohdmi"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."

EXTRA_OEMAKE += "prefix=${D}/usr \
                 sysroot=${STAGING_DIR_TARGET} \
                 LFLAGS="${LDFLAGS}" \
                 BUSID_HAS_NUMBER=1 \
                 BUILD_IN_YOCTO=1 \
                 XSERVER_GREATER_THAN_13=1"

CFLAGS += "-I${STAGING_INCDIR}/xorg \
           -I${STAGING_INCDIR}/drm \
           -I../../DRI_1.10.4/src"

S = "${WORKDIR}/xserver-xorg-video-imx-viv-${PV}/"

PACKAGES =+ "xserver-xorg-extension-viv-autohdmi"

# FIXME: The Freescale provided Makefile has hardcodec include paths
#        and this does not work in case prefix is different than /usr,
#        sed it.
do_configure_prepend () {
    sed -i 's,$(sysroot)/usr/include,${STAGING_INCDIR},g' \
            ${S}EXA/src/makefile.linux
}

do_install_append () {
	install -d ${D}${includedir}
	cp -axr ${S}/EXA/src/vivante_gal/vivante_priv.h ${D}${includedir}
	cp -axr ${S}/EXA/src/vivante_gal/vivante_gal.h ${D}${includedir}

	install -d ${D}/${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/rc.autohdmi ${D}/${sysconfdir}/init.d/rc.autohdmi

	find ${D}${includedir} -type f -exec chmod 660 {} \;
}

RDEPENDS_${PN} += "libvivante-dri-mx6 \
                   xserver-xorg-module-exa \
                   mesa-driver-swrast \
                   xserver-xorg-extension-dri \
                   xserver-xorg-extension-dri2 \
                   xserver-xorg-extension-glx"

REALSOLIBS := "${SOLIBS}"
SOLIBS = "${SOLIBSDEV}"

FILES_${PN} = "${libdir}/*/*/*/vivante_drv${SOLIBS}"
FILES_${PN}-dev = "${includedir} /usr/src ${libdir}/libfsl_x11_ext${SOLIBSDEV}"
FILES_${PN}-dbg = "${libdir}/*/*/*/.debug ${libdir}/.debug/libfsl_x11_ext${SOLIBS} ${exec_prefix}/bin/.debug/autohdmi"

FILES_xserver-xorg-extension-viv-autohdmi = " ${libdir}/libfsl_x11_ext${SOLIBS} ${exec_prefix}/bin/autohdmi ${sysconfdir}/init.d/rc.autohdmi"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx6)"
