# i.MX customization for gstreamer1.0-plugins-base_1.28.1.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# OE-core original.

DEFAULT_PREFERENCE = "-1"

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=69333daa044cb77e486cc36129f7a770"

SRC_URI:remove = "\
    https://gstreamer.freedesktop.org/src/gst-plugins-base/gst-plugins-base-${PV}.tar.xz \
    file://0001-ENGR00312515-get-caps-from-src-pad-when-query-caps.patch \
    file://0003-viv-fb-Make-sure-config.h-is-included.patch \
    file://0002-ssaparse-enhance-SSA-text-lines-parsing.patch"
SRC_URI:prepend = "${GST1.0-PLUGINS-BASE_SRC};branch=${SRCBRANCH} "

# The gl gir patch carries no Signed-off-by. It is an NXP-internal change
# that arrived here without one, and a sign-off is a certification only its
# author can make, so there is nothing here for us to correct.
# nooelint: oelint.file.patchsignedoff
SRC_URI:append:mx93-nxp-bsp = " file://0001-MMFMWK-9590-gstgl-1.0-disable-gir-build-for-gl-plugi.patch"
SRC_URI:append:mx943-nxp-bsp = " file://0001-MMFMWK-9590-gstgl-1.0-disable-gir-build-for-gl-plugi.patch"

GST1.0-PLUGINS-BASE_SRC ?= "gitsm://github.com/nxp-imx/gst-plugins-base.git;protocol=https"
SRCBRANCH = "MM_04.11.00_2605_L6.18.20"
SRCREV = "500aeb1b0dc50198005bd60459a38b42c7359d1d"

inherit use-imx-headers

PACKAGECONFIG:remove = "${PACKAGECONFIG_REMOVE}"
PACKAGECONFIG_REMOVE ?= "jpeg"

PACKAGECONFIG:append = " ${PACKAGECONFIG_G2D}"
PACKAGECONFIG_G2D ??= ""
PACKAGECONFIG_G2D:imxgpu2d ??= "g2d"

PACKAGECONFIG[g2d] = ",,virtual/libg2d"
PACKAGECONFIG[viv-fb] = ",,virtual/libgles2"

EXTRA_OEMESON += "-Dc_args="${CFLAGS} -I${STAGING_INCDIR_IMX}""

# links with imx-gpu libs which are pre-built for glibc
# gcompat will address it during runtime
LDFLAGS:append:imxgpu:libc-musl = " -Wl,--allow-shlib-undefined"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

# The per-plugin split packages (${PN}-*, libgst*) are created dynamically by
# PACKAGES_DYNAMIC, which OE-core sets in gstreamer1.0-plugins-packaging.inc
# (pulled in through the required gstreamer1.0-plugins-common.inc above).
PACKAGES_DYNAMIC = "^${PN}-.* ^libgst.*"
