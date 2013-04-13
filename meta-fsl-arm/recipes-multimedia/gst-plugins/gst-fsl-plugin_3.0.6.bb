# Copyright (C) 2012-2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS = "virtual/kernel gstreamer gst-plugins-base libfslcodec libfslparser"

PR = "${INC_PR}.1"

SRC_URI += "file://fix-missing-sys-types-h.patch \
            file://Link-with-the-Real-Time-Extension-lib.patch \
           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "5416a727e8b0bd94eec077ac7d70aae7"
SRC_URI[sha256sum] = "fdb0b5ab964f607a93664412702a131931da8ef0b557597cbf161c3d9c9d0c12"

COMPATIBLE_MACHINE = "(mx28)"
