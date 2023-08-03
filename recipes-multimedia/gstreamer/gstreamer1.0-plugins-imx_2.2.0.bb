# Copyright (C) 2018 O.S. Systems Software LTDA.
DESCRIPTION = "GStreamer 1.0 plugins for i.MX platforms"
LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=55ca817ccb7d5b5b66355690e9abc605"
SECTION = "multimedia"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base libimxdmabuffer"
# add the audioparsers and the videoparsersbad plugins as RDEPENDS ; audioparsers
# for the uniaudio decoder, videoparsersbad for the VPU video decoder
# the gstreamer1.0-plugins-imx RDEPENDS is necessary to ensure the -good recipe is
# built (it is not a compile-time dependency however, hence RDEPENDS and not DEPENDS)
RDEPENDS:gstreamer1.0-plugins-imx = "gstreamer1.0-plugins-good gstreamer1.0-plugins-bad"
RDEPENDS:gstreamer1.0-plugins-imx-imxaudio = "gstreamer1.0-plugins-good-audioparsers"
RDEPENDS:gstreamer1.0-plugins-imx-imxvpu = "gstreamer1.0-plugins-bad-videoparsersbad"

PV .= "+git${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "cb3cd45676e808b222ac573e8a118f44fd70c288"
SRC_URI = "git://github.com/Freescale/gstreamer-imx.git;branch=${SRCBRANCH};protocol=https"

S = "${WORKDIR}/git"

inherit pkgconfig meson use-imx-headers

# libg2d on i.MX8 SoCs with a DPU is emulated via the DPU.
# That particular libg2d .so depends on libdrm, however.
# Also, due to behavioral differences, an additional flag
# is needed to improve performance.
LIBG2D_DPU_OPTION = "-Dg2d-based-on-dpu=false"
LIBG2D_DEPENDENCIES = "virtual/libg2d"
LIBG2D_DPU_OPTION:imxdpu = "-Dg2d-based-on-dpu=true"
LIBG2D_DEPENDENCIES:imxdpu = "virtual/libg2d libdrm"

# v4l2-amphion and v4l2-isi are meant for the Amphion Malone
# VPU decoder and the ISI drivers in older kernels, which had
# lots of bugs that required nontrivial workarounds. In the
# kernels in this release, newer drivers are present, which
# obviate the need for these special elements.
EXTRA_OEMESON += "-Dimx-headers-path=${STAGING_INCDIR_IMX} -Dv4l2-amphion=disabled -Dv4l2-isi=false"

PACKAGECONFIG ?= "uniaudiodec"
PACKAGECONFIG:append:imxgpu2d = " g2d"
PACKAGECONFIG:append:imxvpu   = " vpu"
PACKAGECONFIG:append:imxipu   = " ipu"
PACKAGECONFIG:append:imxpxp   = " pxp"
# The custom imxv4l2 source and sink elements are only
# available on the i.MX6.
# The 2D blitter sinks require an MXC framebuffer, which
# is not available anymore on the i.MX8 (since these SoCs
# now use KMS instead of the old Linux framebuffer).
PACKAGECONFIG:append:mx6-nxp-bsp      = " imx2dvideosink v4l2-mxc-source-sink"
PACKAGECONFIG:append:mx7-nxp-bsp      = " imx2dvideosink"

PACKAGECONFIG[g2d] = "-Dg2d=enabled ${LIBG2D_DPU_OPTION},-Dg2d=disabled,${LIBG2D_DEPENDENCIES}"
PACKAGECONFIG[pxp] = "-Dpxp=enabled,-Dpxp=disabled,"
PACKAGECONFIG[ipu] = "-Dipu=enabled,-Dipu=disabled,"
PACKAGECONFIG[vpu] = "-Dvpu=enabled,-Dvpu=disabled,libimxvpuapi2"
PACKAGECONFIG[imx2dvideosink] = "-Dimx2d-videosink=true,-Dimx2d-videosink=false,"
PACKAGECONFIG[v4l2-mxc-source-sink] = "-Dv4l2-mxc-source-sink=true,-Dv4l2-mxc-source-sink=false,"
PACKAGECONFIG[uniaudiodec] = "-Duniaudiodec=enabled,-Duniaudiodec=disabled,imx-codec"
PACKAGECONFIG[mp3encoder] = "-Dmp3encoder=enabled,-Dmp3encoder=disabled,imx-codec"

require gstreamer1.0-plugins-packaging.inc

# the following line is required to produce one package for each plugin
PACKAGES_DYNAMIC = "^${PN}-.*"

COMPATIBLE_MACHINE = "(mx6dl-nxp-bsp|mx6q-nxp-bsp|mx6sl-nxp-bsp|mx6sx-nxp-bsp|mx6ul-nxp-bsp|mx6ull-nxp-bsp|mx7d-nxp-bsp|mx8-nxp-bsp)"
