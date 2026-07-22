# Copyright (C) 2014,2016 Freescale Semiconductor
# Copyright 2017-2025 NXP
# Copyright (C) 2012-2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "GStreamer 1.0 plugins for i.MX"
DESCRIPTION = "Freescale/NXP GStreamer 1.0 plugins providing hardware-accelerated \
               multimedia (audio/video codec, capture and sink) elements for \
               i.MX SoCs."
HOMEPAGE = "https://github.com/nxp-imx/imx-gst1.0-plugin"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only & LGPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=fbc093901857fcd118f065f900982c24"

DEPENDS = "\
    ${DEPENDS_IMXGPU} \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    imx-codec \
    imx-parser \
    libinput \
"
DEPENDS:append:mx6-nxp-bsp = " imx-lib"
DEPENDS:append:mx7-nxp-bsp = " imx-lib"
DEPENDS:append:mx8ulp-nxp-bsp = " imx-lib"
DEPENDS:append:mx93-nxp-bsp = " imx-lib"
DEPENDS:append:mx943-nxp-bsp = " imx-lib"
DEPENDS:append:imxvpu = " imx-vpuwrap"
DEPENDS:append:imxfbdev:imxgpu = " libdrm"
DEPENDS_IMXGPU = ""
DEPENDS_IMXGPU:imxgpu = "${DEPENDS_IMX_OPENCL_CONVERTER}"
DEPENDS_IMX_OPENCL_CONVERTER = "imx-opencl-converter"
DEPENDS_IMX_OPENCL_CONVERTER:mx6-nxp-bsp = ""
DEPENDS_IMX_OPENCL_CONVERTER:mx7-nxp-bsp = ""
DEPENDS_IMX_OPENCL_CONVERTER:mx8mm-nxp-bsp = ""

PV = "4.10.3+git${SRCPV}"

SRC_URI = "${IMXGST_SRC};branch=${SRCBRANCH}"
IMXGST_SRC ?= "git://github.com/nxp-imx/imx-gst1.0-plugin.git;protocol=https"
SRCBRANCH = "MM_04.10.03_2512_L6.18.2"
SRCREV = "0565fc515612908a353e8378e24f97de17cc56a6"

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

PACKAGECONFIG ?= ""

# FIXME: Add all features
# feature from excluded mm packages
PACKAGECONFIG[ac3] = ",,imx-ac3codec,imx-ac3codec"
# feature from special mm packages
PACKAGECONFIG[aacp] = ",,imx-aacpcodec,imx-aacpcodec"
MSDEPENDS = "imx-msparser imx-mscodec"
PACKAGECONFIG[wma10dec] = ",,${MSDEPENDS},${MSDEPENDS}"
PACKAGECONFIG[wma8enc] = ",,${MSDEPENDS},${MSDEPENDS}"

PACKAGES =+ "${PN}-tools ${PN}-libgstfsl"

# Deliberately replace the default main-package file list so the (unversioned)
# GStreamer plugin .so files land in ${PN} instead of ${PN}-dev.
# nooelint: oelint.var.filesoverride
FILES:${PN} = "${libdir}/gstreamer-1.0/*.so ${datadir}"
FILES:${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"
FILES:${PN}-dev += "${libdir}/gstreamer-1.0/*.la ${libdir}/pkgconfig/*.pc"
FILES:${PN}-tools += "${bindir}/* ${libdir}/librecorder_engine-1.0${SOLIBS}"
FILES:${PN}-libgstfsl += "${libdir}/libgstfsl-1.0${SOLIBS}"

# Add codec list that the beep plugin run-time depended
BEEP_RDEPENDS = "imx-codec-aac imx-codec-mp3 imx-codec-oggvorbis"
RDEPENDS:${PN} += "${BEEP_RDEPENDS} gstreamer1.0-plugins-good-id3demux imx-parser"
RDEPENDS:${PN}:append:mx8qm-nxp-bsp = " imx-dsp"
RDEPENDS:${PN}:append:mx8qxp-nxp-bsp = " imx-dsp"
RDEPENDS:${PN}:append:mx8dx-nxp-bsp = " imx-dsp"
RDEPENDS:${PN}:append:mx8mp-nxp-bsp = " imx-dsp"
RDEPENDS:${PN}:append:mx8ulp-nxp-bsp = " imx-dsp"

# The plugins pull in codec/parser build dependencies conditionally through
# PACKAGECONFIG, so the static build-deps QA check cannot see them all.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "build-deps"

# Runtime backwards compatibility with the old gst1.0-fsl-plugin name
RREPLACES:${PN} = "gst1.0-fsl-plugin"
RPROVIDES:${PN} = "gst1.0-fsl-plugin"
RCONFLICTS:${PN} = "gst1.0-fsl-plugin"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
