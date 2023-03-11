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

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-libav/gst-libav-${@get_gst_ver('${PV}')}.tar.xz \
           file://0001-libav-Fix-for-APNG-encoder-property-registration.patch \
           "
SRC_URI[sha256sum] = "3fedd10560fcdfaa1b6462cbf79a38c4e7b57d7f390359393fc0cef6dbf27dfe"

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

# These recipes are copies of oe-core 1.20.3 that are not available
# anymore upstream on the master branch.
# The requirement to have them is because they are dependencies of
# the other ones imx specific gstreamer forks on the layer.
# So make their names maching the exisng ones will make it more safe.
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
