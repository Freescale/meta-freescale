SUMMARY = "A library on top of GStreamer for building an RTSP server"
HOMEPAGE = "http://cgit.freedesktop.org/gstreamer/gst-rtsp-server/"
SECTION = "multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=69333daa044cb77e486cc36129f7a770"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

PNREAL = "gst-rtsp-server"

SRC_URI = "https://gstreamer.freedesktop.org/src/${PNREAL}/${PNREAL}-${@get_gst_ver("${PV}")}.tar.xz"

SRC_URI[sha256sum] = "ee402718be9b127f0e5e66ca4c1b4f42e4926ec93ba307b7ccca5dc6cc9794ca"

S = "${WORKDIR}/${PNREAL}-${@get_gst_ver("${PV}")}"

inherit meson pkgconfig upstream-version-is-even gobject-introspection

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Dexamples=disabled \
    -Dtests=disabled \
"

GIR_MESON_ENABLE_FLAG = "enabled"
GIR_MESON_DISABLE_FLAG = "disabled"

# Drop .imx from PV
def get_gst_ver(v):
    return oe.utils.trim_version(v, 3)

# Starting with 1.8.0 gst-rtsp-server includes dependency-less plugins as well
require recipes-multimedia/gstreamer/gstreamer1.0-plugins-packaging.inc

CVE_PRODUCT += "gst-rtsp-server"

# These recipes are copies of oe-core 1.20.3 that are not available
# anymore upstream on the master branch.
# The requirement to have them is because they are dependencies of
# the other ones imx specific gstreamer forks on the layer.
# So make their names maching the exisng ones will make it more safe.
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
