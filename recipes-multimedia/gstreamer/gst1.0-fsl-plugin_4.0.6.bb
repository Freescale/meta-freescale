# Copyright (C) 2014,2015 Freescale Semiconductor
# Copyright (C) 2012-2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Gstreamer freescale plugins"
LICENSE = "GPLv2 & LGPLv2 & LGPLv2.1"
SECTION = "multimedia"

DEPENDS = "libfslcodec libfslparser virtual/kernel gstreamer1.0 gstreamer1.0-plugins-base"
DEPENDS_append_mx6q = " imx-lib imx-vpu libfslvpuwrap"
DEPENDS_append_mx6dl = " imx-lib imx-vpu libfslvpuwrap"
DEPENDS_append_mx6sl = " imx-lib"
DEPENDS_append_mx6sx = " imx-lib"
DEPENDS_append_mx6ul = " imx-lib"
DEPENDS_append_mx7 = " imx-lib"

LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
                    file://COPYING-LGPL-2;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
                    file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

SRC_URI = "${FSL_MIRROR}/gst1.0-fsl-plugins-${PV}.tar.gz"
SRC_URI[md5sum] = "098dfa5e2c0cc7314f526d4aacc4613d"
SRC_URI[sha256sum] = "cb5e066546221120ef06bab6ec3052459d92b997190077dc50545e49d5e3b151"

S = "${WORKDIR}/gst1.0-fsl-plugins-${PV}"

inherit autotools pkgconfig

# Make sure kernel sources are available
do_configure[depends] += "virtual/kernel:do_shared_workdir"

PLATFORM_mx6 = "MX6"
PLATFORM_mx6sl = "MX6SL"
PLATFORM_mx6sx = "MX6SX"
PLATFORM_mx6ul = "MX6UL"
PLATFORM_mx7= "MX7D"

# Todo add a mechanism to map possible build targets
EXTRA_OECONF = "PLATFORM=${PLATFORM} \
                CPPFLAGS="-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include" \
                CROSS_ROOT=${PKG_CONFIG_SYSROOT_DIR}"

PACKAGES =+ "${PN}-gplay ${PN}-libgplaycore ${PN}-libgstfsl ${PN}-grecorder ${PN}-librecorder-engine"

# Add codec list that the beep plugin run-time depended
BEEP_RDEPENDS = "libfslcodec-aac libfslcodec-mp3 libfslcodec-oggvorbis"
RDEPENDS_${PN} += "libfslparser ${BEEP_RDEPENDS} gstreamer1.0-plugins-good-id3demux "

PACKAGECONFIG ?= ""
PACKAGECONFIG_mx6q = "overlaysink"
PACKAGECONFIG_mx6dl = "overlaysink"
PACKAGECONFIG_mx6sx = "overlaysink"
PACKAGECONFIG_mx6sl = "overlaysink"

# FIXME: Add all features
# feature from excluded mm packages
PACKAGECONFIG[ac3] += ",,libfslac3codec,libfslac3codec"
# feature from special mm packages
PACKAGECONFIG[aacp] += ",,libfslaacpcodec,libfslaacpcodec"
MSDEPENDS = "libfslmsparser libfslmscodec"
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

COMPATIBLE_MACHINE = "(mx6|mx7)"
