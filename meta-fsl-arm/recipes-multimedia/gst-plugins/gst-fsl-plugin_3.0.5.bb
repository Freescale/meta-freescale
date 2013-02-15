# Copyright (C) 2012-2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.3"

SRC_URI += "file://fix-missing-sys-types-h.patch \
            file://Link-with-the-Real-Time-Extension-lib.patch \
           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "fd7c9db129cbcc4dc79b7dd832061594"
SRC_URI[sha256sum] = "546deb407993f726ef4e140f5bd734d612d5f8872c832ac820957f667f22306e"

COMPATIBLE_MACHINE = "(mx6)"
