SUMMARY = "Libav-based GStreamer 1.x plugin"
DESCRIPTION = "Contains a GStreamer plugin for using the encoders, decoders, \
muxers, and demuxers provided by FFmpeg."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
SECTION = "multimedia"

# ffmpeg has comercial license flags so add it as we need ffmpeg as a dependency
LICENSE_FLAGS = "commercial"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=69333daa044cb77e486cc36129f7a770 \
                    file://ext/libav/gstav.h;beginline=1;endline=18;md5=a752c35267d8276fd9ca3db6994fca9c \
                    "

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-libav/gst-libav-1.22.5.tar.xz"
SRC_URI[sha256sum] = "8583f0c1f4fcb01eed11fa1e3c21126543a8bd739ed4fc1db31f756a5ab01d9a"

S = "${WORKDIR}/gst-libav-1.22.5"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base ffmpeg"

inherit meson pkgconfig upstream-version-is-even

EXTRA_OEMESON += " \
    -Dtests=disabled \
"

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"
