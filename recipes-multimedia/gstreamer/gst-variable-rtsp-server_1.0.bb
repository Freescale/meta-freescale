# Copyright (C) 2017 NXP Semiconductor

SUMMARY = "RTSP server for live-stream from a v4l2 video source"
DESCRIPTION = "GStreamer-based RTSP server that streams live video from a V4L2 \
               source with a variable bitrate."
HOMEPAGE = "https://github.com/Gateworks/gst-gateworks-apps"
SECTION = "multimedia"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "glib-2.0 gstreamer1.0 gstreamer1.0-rtsp-server"

SRC_URI = "\
    git://github.com/Gateworks/gst-gateworks-apps;branch=master;protocol=https \
"

SRCREV = "490564815d8049dbdd79087f546835b673ba6e88"

inherit pkgconfig

do_install() {
    install -m 0755 -D ${S}/bin/gst-variable-rtsp-server \
        ${D}/${bindir}/gst-variable-rtsp-server
}
