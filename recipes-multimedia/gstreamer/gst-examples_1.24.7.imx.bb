# This builds an older version which is compatible with the gstreamer NXP fork 1.24.7.imx,
# thus the version is appended with '.imx'

SUMMARY = "GStreamer examples (including gtk-play, gst-play)"
DESCRIPTION = "GStreamer example applications."
HOMEPAGE = "https://gitlab.freedesktop.org/gstreamer/gst-examples"
BUGTRACKER = "https://gitlab.freedesktop.org/gstreamer/gst-examples/-/issues"
LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://playback/player/gtk/gtk-play.c;beginline=1;endline=20;md5=f8c72dae3d36823ec716a9ebcae593b9"

DEPENDS = "glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad gtk+3 json-glib glib-2.0-native"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gstreamer.git;protocol=https;branch=1.24 \
           file://0001-Make-player-examples-installable.patch \
           file://gst-player.desktop \
           "

SRCREV = "0f1e984e41ce8d6d0758265d35e8b10d5815fde2"

S = "${UNPACKDIR}/${BP}/subprojects/gst-examples"

inherit meson pkgconfig features_check

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+\.(\d*[02468])+(\.\d+)+)"

ANY_OF_DISTRO_FEATURES = "${GTK3DISTROFEATURES}"

do_install:append() {
	install -m 0644 -D ${UNPACKDIR}/gst-player.desktop ${D}${datadir}/applications/gst-player.desktop
}

RDEPENDS:${PN} = "gstreamer1.0-plugins-base-playback"
RRECOMMENDS:${PN} = "gstreamer1.0-plugins-base-meta \
                     gstreamer1.0-plugins-good-meta \
                     gstreamer1.0-plugins-bad-meta \
                      ${@bb.utils.contains("LICENSE_FLAGS_ACCEPTED", "commercial", "gstreamer1.0-libav", "", d)} \
                     ${@bb.utils.contains("LICENSE_FLAGS_ACCEPTED", "commercial", "gstreamer1.0-plugins-ugly-meta", "", d)}"
RPROVIDES:${PN} += "gst-player gst-player-bin"
