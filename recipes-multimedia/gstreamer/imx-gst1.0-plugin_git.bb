# Copyright (C) 2014,2016 Freescale Semiconductor
# Copyright 2017-2021 NXP
# Copyright (C) 2012-2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Gstreamer freescale plugins"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only & LGPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=fbc093901857fcd118f065f900982c24"

DEPENDS = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
    imx-codec \
    imx-parser \
    ${DEPENDS_IMXGPU} \
"
DEPENDS:append:mx6-nxp-bsp = " imx-lib"
DEPENDS:append:mx7-nxp-bsp = " imx-lib"
DEPENDS:append:mx8ulp-nxp-bsp = " imx-lib"
DEPENDS:append:mx93-nxp-bsp = " imx-lib"
DEPENDS:append:imxvpu = " imx-vpuwrap"
DEPENDS:append:imxfbdev:imxgpu = " libdrm"
DEPENDS_IMXGPU        = ""
DEPENDS_IMXGPU:imxgpu = "${DEPENDS_IMX_OPENCL_CONVERTER}"
DEPENDS_IMX_OPENCL_CONVERTER               = "imx-opencl-converter"
DEPENDS_IMX_OPENCL_CONVERTER:mx6-nxp-bsp   = ""
DEPENDS_IMX_OPENCL_CONVERTER:mx7-nxp-bsp   = ""
DEPENDS_IMX_OPENCL_CONVERTER:mx8mm-nxp-bsp = ""

# For backwards compatibility
RREPLACES:${PN}  = "gst1.0-fsl-plugin"
RPROVIDES:${PN}  = "gst1.0-fsl-plugin"
RCONFLICTS:${PN} = "gst1.0-fsl-plugin"

PV = "4.10.0+git${SRCPV}"

SRC_URI = " \
    git://github.com/nxp-imx/imx-gst1.0-plugin.git;protocol=https;branch=${SRCBRANCH} \
    file://0001-gplay2-fix-termio.h-no-longer-existing.patch \
"
SRCBRANCH = "MM_04.10.0_2505_L6.12.20"
SRCREV = "6861aec99901375f5ebcd5170ce1f5232cd38fd6"

S = "${WORKDIR}/git"

inherit meson pkgconfig use-imx-headers

PLATFORM:mx6-nxp-bsp = "MX6"
PLATFORM:mx6sl-nxp-bsp = "MX6SL"
PLATFORM:mx6sx-nxp-bsp = "MX6SX"
PLATFORM:mx6ul-nxp-bsp = "MX6UL"
PLATFORM:mx6sll-nxp-bsp = "MX6SLL"
PLATFORM:mx7-nxp-bsp = "MX7D"
PLATFORM:mx7ulp-nxp-bsp = "MX7ULP"
PLATFORM:mx8-nxp-bsp = "MX8"
PLATFORM:mx9-nxp-bsp = "MX9"

# Todo add a mechanism to map possible build targets
EXTRA_OEMESON = "-Dplatform=${PLATFORM} \
                 -Dc_args="${CFLAGS} -I${STAGING_INCDIR_IMX}" \
"

PACKAGES =+ "${PN}-gplay ${PN}-libgplaycore ${PN}-libgstfsl ${PN}-grecorder ${PN}-librecorder-engine ${PN}-libplayengine"

# Add codec list that the beep plugin run-time depended
BEEP_RDEPENDS = "imx-codec-aac imx-codec-mp3 imx-codec-oggvorbis"
RDEPENDS:${PN} += "imx-parser ${BEEP_RDEPENDS} gstreamer1.0-plugins-good-id3demux "
RDEPENDS:${PN}:append:mx8qm-nxp-bsp  = " imx-dsp"
RDEPENDS:${PN}:append:mx8qxp-nxp-bsp = " imx-dsp"
RDEPENDS:${PN}:append:mx8dx-nxp-bsp  = " imx-dsp"
RDEPENDS:${PN}:append:mx8mp-nxp-bsp  = " imx-dsp"
RDEPENDS:${PN}:append:mx8ulp-nxp-bsp = " imx-dsp"

PACKAGECONFIG ?= ""

# FIXME: Add all features
# feature from excluded mm packages
PACKAGECONFIG[ac3] = ",,imx-ac3codec,imx-ac3codec"
# feature from special mm packages
PACKAGECONFIG[aacp] = ",,imx-aacpcodec,imx-aacpcodec"
MSDEPENDS = "imx-msparser imx-mscodec"
PACKAGECONFIG[wma10dec] = ",,${MSDEPENDS},${MSDEPENDS}"
PACKAGECONFIG[wma8enc] = ",,${MSDEPENDS},${MSDEPENDS}"

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
