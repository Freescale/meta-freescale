# Copyright (C) 2014,2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Copyright (C) 2012-2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Gstreamer freescale plugins"
LICENSE = "GPLv2 & LGPLv2 & LGPLv2.1"
SECTION = "multimedia"

DEPENDS = "imx-codec imx-parser gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"
DEPENDS_append_mx6 = " imx-lib"
DEPENDS_append_mx7 = " imx-lib"
DEPENDS_append_imxvpu = " imx-vpuwrap libdrm"

# For backwards compatibility
RREPLACES_${PN} = "gst1.0-fsl-plugin"
RPROVIDES_${PN} = "gst1.0-fsl-plugin"
RCONFLICTS_${PN} = "gst1.0-fsl-plugin"

LIC_FILES_CHKSUM = "file://COPYING-LGPL-2;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
                    file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

IMXGST_SRC ?= "git://source.codeaurora.org/external/imx/imx-gst1.0-plugin.git;protocol=https"
SRCBRANCH = "MM_04.04.05_1902_L4.14.98_GA"

SRC_URI = "${IMXGST_SRC};branch=${SRCBRANCH}"
SRCREV = "0c5f48b3aee473d8c8f3e853eb2078f054caa4d2"

S = "${WORKDIR}/git"

inherit autotools pkgconfig use-imx-headers

PLATFORM_mx6 = "MX6"
PLATFORM_mx6sl = "MX6SL"
PLATFORM_mx6sx = "MX6SX"
PLATFORM_mx6ul = "MX6UL"
PLATFORM_mx6sll = "MX6SLL"
PLATFORM_mx7= "MX7D"
PLATFORM_mx7ulp= "MX7ULP"
PLATFORM_mx8 = "MX8"

# Todo add a mechanism to map possible build targets
EXTRA_OECONF = "PLATFORM=${PLATFORM} \
                CPPFLAGS="-I${STAGING_INCDIR_IMX}" \
                CROSS_ROOT=${PKG_CONFIG_SYSROOT_DIR} \
                ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', bb.utils.contains('DISTRO_FEATURES', 'x11', '--disable-x11', '', d), '', d)}"

PACKAGES =+ "${PN}-gplay ${PN}-libgplaycore ${PN}-libgstfsl ${PN}-grecorder ${PN}-librecorder-engine ${PN}-libplayengine"

# Add codec list that the beep plugin run-time depended
BEEP_RDEPENDS = "imx-codec-aac imx-codec-mp3 imx-codec-oggvorbis"
RDEPENDS_${PN} += "imx-parser ${BEEP_RDEPENDS} gstreamer1.0-plugins-good-id3demux "

# overlaysink rely on G2D,
# cannot be supported on i.MX6SLL & i.MX6UL & i.MX6ULL & i.MX7D
PACKAGECONFIG ?= ""
PACKAGECONFIG_imxgpu2d = "overlaysink"


# FIXME: Add all features
# feature from excluded mm packages
PACKAGECONFIG[ac3] += ",,imx-ac3codec,imx-ac3codec"
# feature from special mm packages
PACKAGECONFIG[aacp] += ",,imx-aacpcodec,imx-aacpcodec"
MSDEPENDS = "imx-msparser imx-mscodec"
PACKAGECONFIG[wma10dec] += ",,${MSDEPENDS},${MSDEPENDS}"
PACKAGECONFIG[wma8enc] += "--enable-wma8enc,--disable-wma8enc,${MSDEPENDS},${MSDEPENDS}"
OVDEPENDS = "virtual/libg2d"
PACKAGECONFIG[overlaysink] += "--enable-overlaysink,--disable-overlaysink, ${OVDEPENDS}"

FILES_${PN} = "${libdir}/gstreamer-1.0/*.so ${datadir}"

FILES_${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0/*.la ${libdir}/pkgconfig/*.pc"
FILES_${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"
FILES_${PN}-gplay = "${bindir}/gplay-1.0"
FILES_${PN}-libgplaycore = "${libdir}/libgplaycore-1.0${SOLIBS}"
FILES_${PN}-libgstfsl = "${libdir}/libgstfsl-1.0${SOLIBS}"
FILES_${PN}-grecorder = "${bindir}/grecorder-1.0"
FILES_${PN}-librecorder-engine = "${libdir}/librecorder_engine-1.0${SOLIBS}"
FILES_${PN}-libplayengine = "${libdir}/libplayengine-1.0${SOLIBS}"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
