# Copyright (C) 2011-2013 Freescale Semiconductor
# Copytight (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Gstreamer freescale plugins"
LICENSE = "GPLv2 & LGPLv2 & LGPLv2.1"
SECTION = "multimedia"

DEPENDS = "libfslcodec libfslparser virtual/kernel"
DEPENDS_append_mx5 = " imx-lib imx-vpu"
DEPENDS_append_mx6q = " imx-lib imx-vpu libfslvpuwrap"
DEPENDS_append_mx6dl = " imx-lib imx-vpu libfslvpuwrap"
DEPENDS_append_mx6sl = " imx-lib"
DEPENDS_append_mx6sx = " imx-lib"

LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
                    file://COPYING-LGPL-2;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
                    file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

inherit autotools pkgconfig

PLATFORM_mx5 = "MX51"
PLATFORM_mx6 = "MX6"
PLATFORM_mx6sl = "MX6SL"
PLATFORM_mx6sx = "MX6SX"
PLATFORM_mx28 = "MX28"

# Todo add a mechanism to map posible build targets
EXTRA_OECONF = "PLATFORM=${PLATFORM} \
                CPPFLAGS="-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include""

PACKAGES =+ "${PN}-libmfwsconf ${PN}-libgplaycore ${PN}-libgstfsl \
             ${PN}-libme ${PN}-libgstbufmeta ${PN}-libmfwba \
             ${PN}-libfwvss ${PN}-gplay"

# Add codec list that the beep plugin run-time depended
BEEP_RDEPENDS = "libfslcodec-aac libfslcodec-mp3 libfslcodec-oggvorbis"
RDEPENDS_${PN} += "gst-plugins-good-id3demux libfslparser ${BEEP_RDEPENDS}"

FILES_${PN}-libmfwsconf = "${libdir}/libmfwsconf${SOLIBS}"
FILES_${PN}-libme = "${libdir}/libme${SOLIBS}"
FILES_${PN}-libgstbufmeta = "${libdir}/libgstbufmeta${SOLIBS}"
FILES_${PN}-libmfwba =  "${libdir}/libmfwba${SOLIBS}"
FILES_${PN}-libfwvss = "${libdir}/libmfwvss${SOLIBS}"
