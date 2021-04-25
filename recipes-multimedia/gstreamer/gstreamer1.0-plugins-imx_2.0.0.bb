# Copyright (C) 2018 O.S. Systems Software LTDA.
DESCRIPTION = "GStreamer 1.0 plugins for i.MX platforms"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=55ca817ccb7d5b5b66355690e9abc605"
SECTION = "multimedia"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base libimxdmabuffer"
# add the audioparsers and the videoparsersbad plugins as RDEPENDS ; audioparsers
# for the uniaudio decoder, videoparsersbad for the VPU video decoder
# the gstreamer1.0-plugins-imx RDEPENDS is necessary to ensure the -good recipe is
# built (it is not a compile-time dependency however, hence RDEPENDS and not DEPENDS)
RDEPENDS_gstreamer1.0-plugins-imx = "gstreamer1.0-plugins-good gstreamer1.0-plugins-bad"
RDEPENDS_gstreamer1.0-plugins-imx-imxaudio = "gstreamer1.0-plugins-good-audioparsers"
RDEPENDS_gstreamer1.0-plugins-imx-imxvpu = "gstreamer1.0-plugins-bad-videoparsersbad"

PV .= "+git${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "11e3a555a280f97d55d5243e8259a255b3ed14d0"
SRC_URI = "git://github.com/Freescale/gstreamer-imx.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit pkgconfig meson use-imx-headers

# libg2d on i.MX8 SoCs with a DPU is emulated via the DPU.
# That particular libg2d .so depends on libdrm, however.
# Also, due to behavioral differences, an additional flag
# is needed to improve performance.
LIBG2D_DPU_OPTION = "-Dg2d-based-on-dpu=false"
LIBG2D_DEPENDENCIES = "virtual/libg2d"
LIBG2D_DPU_OPTION_imxdpu = "-Dg2d-based-on-dpu=true"
LIBG2D_DEPENDENCIES_imxdpu = "virtual/libg2d libdrm"

EXTRA_OEMESON += "-Dimx-headers-path=${STAGING_INCDIR_IMX}"

PACKAGECONFIG ?= "uniaudiodec"
PACKAGECONFIG_append_imxgpu2d = " g2d"
PACKAGECONFIG_append_imxvpu   = " vpu"
PACKAGECONFIG_append_imxipu   = " ipu"
PACKAGECONFIG_append_imxpxp   = " pxp"
# The custom imxv4l2 elements are only available on the i.MX6.
# The 2D blitter sinks require an MXC framebuffer, which
# is not available anymore on the i.MX8 (since these SoCs
# now use KMS instead of the old Linux framebuffer).
PACKAGECONFIG_append_mx6      = " imx2dvideosink v4l2"
PACKAGECONFIG_append_mx7      = " imx2dvideosink"

PACKAGECONFIG[g2d] = "-Dg2d=enabled ${LIBG2D_DPU_OPTION},-Dg2d=disabled,${LIBG2D_DEPENDENCIES}"
PACKAGECONFIG[pxp] = "-Dpxp=enabled,-Dpxp=disabled,"
PACKAGECONFIG[ipu] = "-Dipu=enabled,-Dipu=disabled,"
PACKAGECONFIG[vpu] = "-Dvpu=enabled,-Dvpu=disabled,libimxvpuapi2"
PACKAGECONFIG[imx2dvideosink] = "-Dimx2d-videosink=true,-Dimx2d-videosink=false,"
PACKAGECONFIG[v4l2] = "-Dv4l2=true,-Dv4l2=false,"
PACKAGECONFIG[uniaudiodec] = "-Duniaudiodec=enabled,-Duniaudiodec=disabled,imx-codec"
PACKAGECONFIG[mp3encoder] = "-Dmp3encoder=enabled,-Dmp3encoder=disabled,imx-codec"

require recipes-multimedia/gstreamer/gstreamer1.0-plugins-packaging.inc

# the following line is required to produce one package for each plugin
PACKAGES_DYNAMIC = "^${PN}-.*"

COMPATIBLE_MACHINE = "(mx6dl|mx6q|mx6sl|mx6sx|mx6ul|mx6ull|mx7d|mx8)"
