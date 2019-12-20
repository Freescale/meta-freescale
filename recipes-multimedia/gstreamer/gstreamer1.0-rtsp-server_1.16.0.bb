SUMMARY = "A library on top of GStreamer for building an RTSP server"
HOMEPAGE = "http://cgit.freedesktop.org/gstreamer/gst-rtsp-server/"
SECTION = "multimedia"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

PNREAL = "gst-rtsp-server"

FILESEXTRAPATHS_prepend := "${BSPDIR}/sources/poky/meta/recipes-multimedia/gstreamer/${PN}:"
FILESEXTRAPATHS_prepend := "${BSPDIR}/sources/poky/meta/recipes-multimedia/gstreamer/files:"

SRC_URI = "http://gstreamer.freedesktop.org/src/${PNREAL}/${PNREAL}-${PV}.tar.xz \
           file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
           file://gtk-doc-tweaks.patch \
           "

SRC_URI[md5sum] = "adc4460239ec2eccf58ad9752ce53bfd"
SRC_URI[sha256sum] = "198e9eec1a3e32dc810d3fbf3a714850a22c6288d4a5c8e802c5ff984af03f19"

S = "${WORKDIR}/${PNREAL}-${PV}"

inherit autotools pkgconfig upstream-version-is-even gobject-introspection gtk-doc

EXTRA_OECONF = "--disable-examples --disable-tests --disable-introspection "

# Starting with 1.8.0 gst-rtsp-server includes dependency-less plugins as well
LIBV = "1.0"
require recipes-multimedia/gstreamer/gst-plugins-package.inc

delete_pkg_m4_file() {
        # This m4 file is out of date and is missing PKG_CONFIG_SYSROOT_PATH tweaks which we need for introspection
        rm "${S}/common/m4/pkg.m4" || true
}

do_configure[prefuncs] += " delete_pkg_m4_file"
