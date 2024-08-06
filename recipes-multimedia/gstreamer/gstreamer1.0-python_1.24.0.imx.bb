SUMMARY = "Python bindings for GStreamer 1.0"
DESCRIPTION = "GStreamer Python binding overrides (complementing the bindings \
provided by python-gi) "
HOMEPAGE = "http://cgit.freedesktop.org/gstreamer/gst-python/"
SECTION = "multimedia"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=c34deae4e395ca07e725ab0076a5f740"

SRC_URI = "\
    https://gstreamer.freedesktop.org/src/${PNREAL}/${PNREAL}-${@get_gst_ver("${PV}")}.tar.xz \
    file://0001-meson.build-Fix-missing-python_opt.patch \
    "
SRC_URI[sha256sum] = "041c2255c1ea9936c777dcb08a36ecaa6a24a69a12fc46ef53f1530d46c59f9d"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base python3-pygobject"
RDEPENDS:${PN} += "gstreamer1.0 gstreamer1.0-plugins-base python3-pygobject"

PNREAL = "gst-python"

S = "${WORKDIR}/${PNREAL}-${@get_gst_ver('${PV}')}"

EXTRA_OEMESON += "\
    -Dtests=disabled \
    -Dplugin=enabled \
    -Dlibpython-dir=${libdir} \
"

inherit meson pkgconfig setuptools3-base upstream-version-is-even features_check

# Drop .imx from PV
def get_gst_ver(v):
    return oe.utils.trim_version(v, 3)

FILES:${PN} += "${libdir}/gstreamer-1.0"

REQUIRED_DISTRO_FEATURES = "gobject-introspection-data"
