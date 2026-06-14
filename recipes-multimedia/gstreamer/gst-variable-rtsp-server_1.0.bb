# Copyright (C) 2017 NXP Semiconductor

SUMMARY = "RTSP server for live-stream from a v4l2 video source"
HOMEPAGE = "https://github.com/Gateworks/gst-gateworks-apps"
SECTION = "multimedia"

LICENSE = "GPL-3.0-only"

inherit pkgconfig

DEPENDS = "gstreamer1.0 gstreamer1.0-rtsp-server glib-2.0"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = " \
    git://github.com/Gateworks/gst-gateworks-apps;branch=master;protocol=https \
"

SRCREV = "490564815d8049dbdd79087f546835b673ba6e88"


do_install() {
    install -m 0755 -D ${S}/bin/gst-variable-rtsp-server \
        ${D}/${bindir}/gst-variable-rtsp-server
}
