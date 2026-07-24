SUMMARY = "A library on top of GStreamer for building an RTSP server"
HOMEPAGE = "http://cgit.freedesktop.org/gstreamer/gst-rtsp-server/"
SECTION = "multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=69333daa044cb77e486cc36129f7a770"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

PNREAL = "gst-rtsp-server"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI = "https://gstreamer.freedesktop.org/src/${PNREAL}/${PNREAL}-${PV}.tar.xz \
           file://0001-YOCIMX-9113-rtsp-examples-install-test-launch-and-te.patch \
           "

SRC_URI[sha256sum] = "6cd0e86af20cfb749bdc24d2f5c30f4e60a3e8d198ae4e02e754b9937bdbf832"

S = "${UNPACKDIR}/${PNREAL}-${PV}"

inherit meson pkgconfig upstream-version-is-even gobject-introspection

EXTRA_OEMESON += "\
    -Ddoc=disabled \
    -Dexamples=enabled \
    -Dtests=disabled \
"

GIR_MESON_ENABLE_FLAG = "enabled"
GIR_MESON_DISABLE_FLAG = "disabled"

# Starting with 1.8.0 gst-rtsp-server includes dependency-less plugins as well
require recipes-multimedia/gstreamer/gstreamer1.0-plugins-packaging.inc

CVE_PRODUCT += "gst-rtsp-server"

########### i.MX overrides ################

EXTRA_OEMESON:append:mx93-nxp-bsp = " -Dintrospection=disabled "
EXTRA_OEMESON:append:mx943-nxp-bsp = " -Dintrospection=disabled "

########### End of i.MX overrides #########
