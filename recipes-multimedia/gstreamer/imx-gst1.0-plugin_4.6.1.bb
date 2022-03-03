# Copyright (C) 2014,2016 Freescale Semiconductor
# Copyright 2017-2021 NXP
# Copyright (C) 2012-2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Gstreamer freescale plugins"
LICENSE = "GPL-2.0-only & LGPL-2.0-only & LGPL-2.1-only"
SECTION = "multimedia"

DEPENDS = "imx-codec imx-parser gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"
DEPENDS:append:mx6-nxp-bsp = " imx-lib"
DEPENDS:append:mx7-nxp-bsp = " imx-lib"
DEPENDS:append:imxvpu = " imx-vpuwrap libdrm"

# For backwards compatibility
RREPLACES:${PN} = "gst1.0-fsl-plugin"
RPROVIDES:${PN} = "gst1.0-fsl-plugin"
RCONFLICTS:${PN} = "gst1.0-fsl-plugin"

LIC_FILES_CHKSUM = "file://COPYING-LGPL-2;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
                    file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

SRCBRANCH = "MM_04.06.01_2105_L5.10.y"


SRC_URI = "git://source.codeaurora.org/external/imx/imx-gst1.0-plugin.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "057e6bfbc208ce31e8ed0af0264dd1e86de05808"

S = "${WORKDIR}/git"

inherit meson pkgconfig use-imx-headers

PLATFORM:mx6-nxp-bsp = "MX6"
PLATFORM:mx6sl-nxp-bsp = "MX6SL"
PLATFORM:mx6sx-nxp-bsp = "MX6SX"
PLATFORM:mx6ul-nxp-bsp = "MX6UL"
PLATFORM:mx6sll-nxp-bsp = "MX6SLL"
PLATFORM:mx7-nxp-bsp= "MX7D"
PLATFORM:mx7ulp-nxp-bsp= "MX7ULP"
PLATFORM:mx8-nxp-bsp = "MX8"

# Todo add a mechanism to map possible build targets
EXTRA_OEMESON = "-Dplatform=${PLATFORM} \
                 -Dc_args="${CFLAGS} -I${STAGING_INCDIR_IMX}" \
"

PACKAGES =+ "${PN}-gplay ${PN}-libgplaycore ${PN}-libgstfsl ${PN}-grecorder ${PN}-librecorder-engine ${PN}-libplayengine"

# Add codec list that the beep plugin run-time depended
BEEP_RDEPENDS = "imx-codec-aac imx-codec-mp3 imx-codec-oggvorbis"
RDEPENDS:${PN} += "imx-parser ${BEEP_RDEPENDS} gstreamer1.0-plugins-good-id3demux "
RDEPENDS:${PN}:append:mx8qm-nxp-bsp = " imx-dsp"
RDEPENDS:${PN}:append:mx8qxp-nxp-bsp = " imx-dsp"
RDEPENDS:${PN}:append:mx8mp-nxp-bsp = " imx-dsp"
RDEPENDS:${PN}:append:mx8ulp-nxp-bsp = " imx-dsp"

# overlaysink rely on G2D,
# cannot be supported on i.MX6SLL & i.MX6UL & i.MX6ULL & i.MX7D
PACKAGECONFIG ?= ""
PACKAGECONFIG:imxgpu2d = "overlaysink"


# FIXME: Add all features
# feature from excluded mm packages
PACKAGECONFIG[ac3] = ",,imx-ac3codec,imx-ac3codec"
# feature from special mm packages
PACKAGECONFIG[aacp] = ",,imx-aacpcodec,imx-aacpcodec"
MSDEPENDS = "imx-msparser imx-mscodec"
PACKAGECONFIG[wma10dec] = ",,${MSDEPENDS},${MSDEPENDS}"
PACKAGECONFIG[wma8enc] = ",,${MSDEPENDS},${MSDEPENDS}"
PACKAGECONFIG[overlaysink] += ",,virtual/libg2d"

FILES:${PN} = "${libdir}/gstreamer-1.0/*.so ${datadir}"

FILES:${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"
FILES:${PN}-dev += "${libdir}/gstreamer-1.0/*.la ${libdir}/pkgconfig/*.pc"
FILES:${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"
FILES:${PN}-gplay = "${bindir}/gplay-1.0"
FILES:${PN}-libgplaycore = "${libdir}/libgplaycore-1.0${SOLIBS}"
FILES:${PN}-libgstfsl = "${libdir}/libgstfsl-1.0${SOLIBS}"
FILES:${PN}-grecorder = "${bindir}/grecorder-1.0"
FILES:${PN}-librecorder-engine = "${libdir}/librecorder_engine-1.0${SOLIBS}"
FILES:${PN}-libplayengine = "${libdir}/libplayengine-1.0${SOLIBS}"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
