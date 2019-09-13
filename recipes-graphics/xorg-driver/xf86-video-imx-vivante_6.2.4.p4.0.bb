# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/xorg-driver/xorg-driver-video.inc

PE = "3"

inherit autotools-brokensep update-rc.d pkgconfig

DEPENDS += "virtual/xserver virtual/libx11 libgal-imx imx-gpu-viv virtual/libg2d pixman"

LIC_FILES_CHKSUM = "file://COPYING-MIT;md5=b5e9d9f5c02ea831ab3ecf802bb7c4f3"

SRCREV = "b765c3ffc0dcc4246afa659aa0d59f78a6736b08"
SRCBRANCH = "imx_exa_viv6_g2d"
SRC_URI = "git://source.codeaurora.org/external/imx/xf86-video-imx-vivante.git;protocol=https;branch=${SRCBRANCH} \
           file://rc.autohdmi"

S = "${WORKDIR}/git/"

INITSCRIPT_PACKAGES = "xserver-xorg-extension-viv-autohdmi"
INITSCRIPT_NAME = "rc.autohdmi"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."

EXTRA_OEMAKE += "prefix=${exec_prefix} \
                 sysroot=${STAGING_DIR_TARGET} \
                 SDKTARGETSYSROOT=${STAGING_DIR_HOST} \
                 BUSID_HAS_NUMBER=1 \
                 BUILD_IN_YOCTO=1 \
                 XSERVER_GREATER_THAN_13=1"
TARGET_CC_ARCH += "${LDFLAGS}"

PACKAGES =+ "xserver-xorg-extension-viv-autohdmi"

do_install_append () {
    install -d ${D}${includedir}
    cp -axr ${S}/EXA/src/vivante_gal/vivante_priv.h ${D}${includedir}
    cp -axr ${S}/EXA/src/vivante_gal/vivante_gal.h ${D}${includedir}

    install -d ${D}/${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/rc.autohdmi ${D}/${sysconfdir}/init.d/rc.autohdmi

    find ${D}${includedir} -type f -exec chmod 660 {} \;
}

RDEPENDS_${PN} += "libvivante-dri-imx \
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
COMPATIBLE_MACHINE = "(mx6|mx7ulp)"
