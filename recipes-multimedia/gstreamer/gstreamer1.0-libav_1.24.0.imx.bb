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

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-libav/gst-libav-${@get_gst_ver('${PV}')}.tar.xz"
SRC_URI[sha256sum] = "ab775d5b9a7c84176b29822b68c7a34731280039a20b7db5eb639eddd1466178"

S = "${WORKDIR}/gst-libav-${@get_gst_ver('${PV}')}"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base ffmpeg"

inherit meson pkgconfig upstream-version-is-even

EXTRA_OEMESON += " \
    -Dtests=disabled \
"

# Drop .imx from PV
def get_gst_ver(v):
    return oe.utils.trim_version(v, 3)

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
