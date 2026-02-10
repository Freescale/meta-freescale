SUMMARY = "A library on top of GStreamer for building an RTSP server"
HOMEPAGE = "http://cgit.freedesktop.org/gstreamer/gst-rtsp-server/"
SECTION = "multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=69333daa044cb77e486cc36129f7a770"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

# Using the smart version extraction we learned earlier
PV = "1.26.6.imx"
UPSTREAM_VER = "${@d.getVar('PV').split('.imx')[0]}"

PNREAL = "gst-rtsp-server"

# Removed FILESEXTRAPATHS and the missing patch
SRC_URI = "https://gstreamer.freedesktop.org/src/${PNREAL}/${PNREAL}-${UPSTREAM_VER}.tar.xz"
SRC_URI[sha256sum] = "ce4e0b7dde7f17dc4f39ffb2dd3db64b0222d11d58be1c9820c20d30370d6f90"

S = "${UNPACKDIR}/${PNREAL}-${UPSTREAM_VER}"

inherit meson pkgconfig upstream-version-is-even gobject-introspection

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Dexamples=enabled \
    -Dtests=disabled \
"

GIR_MESON_ENABLE_FLAG = "enabled"
GIR_MESON_DISABLE_FLAG = "disabled"

require recipes-multimedia/gstreamer/gstreamer1.0-plugins-packaging.inc

CVE_PRODUCT += "gst-rtsp-server"
