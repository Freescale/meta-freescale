SUMMARY = "Python bindings for GStreamer 1.0"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=c34deae4e395ca07e725ab0076a5f740"

# 1. Get the clean version "1.26.6" from the filename "1.26.6.imx"
# This allows us to reuse this variable for the URL and Directory.
UPSTREAM_VER = "${@d.getVar('PV').split('.imx')[0]}"

PNREAL = "gst-python"

# 2. Fetch URL
SRC_URI = "https://gstreamer.freedesktop.org/src/${PNREAL}/${PNREAL}-${UPSTREAM_VER}.tar.xz;downloadfilename=gst-python-${UPSTREAM_VER}-fresh.tar.xz"
# Note: Keeping the hash that is currently working for your download
SRC_URI[sha256sum] = "a4b5bfe039b7267fe8cdd48d106ab20cf88cda600f800d84f3d07724079edf1f"

# 3. FIX: Point S to the directory name "gst-python-1.26.6"
# (Previous attempt used 1.26.9, which caused the error)
S = "${UNPACKDIR}/gst-python-${UPSTREAM_VER}"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base python3-pygobject"
DEPENDS:append = " gstreamer1.0-plugins-bad"
RDEPENDS:${PN} += "gstreamer1.0 gstreamer1.0-plugins-base python3-pygobject"

inherit meson pkgconfig setuptools3-base features_check

EXTRA_OEMESON += "\
    -Dtests=disabled \
    -Dplugin=enabled \
    -Dlibpython-dir=${libdir} \
"

CFLAGS += "-Wno-error=implicit-function-declaration"

FILES:${PN} += "${libdir}/gstreamer-1.0"
REQUIRED_DISTRO_FEATURES = "gobject-introspection-data"

# 4. Patch for 1.26.0 Core compatibility
do_configure:prepend() {
    if [ -f "${S}/gi/overrides/gstmodule.c" ]; then
        sed -i 's/gst_structure_is_writable/_gst_structure_is_writable/g' ${S}/gi/overrides/gstmodule.c
    else
        bbfatal "Could not find gstmodule.c in ${S}/gi/overrides/"
    fi
}
