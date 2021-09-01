# Copyright (C) 2014,2016 Freescale Semiconductor
# Copyright 2017-2021 NXP
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

SRCBRANCH = "MM_04.06.01_2105_L5.10.y"


SRC_URI = "git://source.codeaurora.org/external/imx/imx-gst1.0-plugin.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "057e6bfbc208ce31e8ed0af0264dd1e86de05808"

S = "${WORKDIR}/git"

inherit meson pkgconfig use-imx-headers

PLATFORM_mx6 = "MX6"
PLATFORM_mx6sl = "MX6SL"
PLATFORM_mx6sx = "MX6SX"
PLATFORM_mx6ul = "MX6UL"
PLATFORM_mx6sll = "MX6SLL"
PLATFORM_mx7= "MX7D"
PLATFORM_mx7ulp= "MX7ULP"
PLATFORM_mx8 = "MX8"

# Todo add a mechanism to map possible build targets
EXTRA_OEMESON = "-Dplatform=${PLATFORM} \
                 -Dc_args="${CFLAGS} -I${STAGING_INCDIR_IMX}" \
"

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
PACKAGECONFIG[ac3] = ",,imx-ac3codec,imx-ac3codec"
# feature from special mm packages
PACKAGECONFIG[aacp] = ",,imx-aacpcodec,imx-aacpcodec"
MSDEPENDS = "imx-msparser imx-mscodec"
PACKAGECONFIG[wma10dec] = ",,${MSDEPENDS},${MSDEPENDS}"
PACKAGECONFIG[wma8enc] = ",,${MSDEPENDS},${MSDEPENDS}"
PACKAGECONFIG[overlaysink] += ",,virtual/libg2d"

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
