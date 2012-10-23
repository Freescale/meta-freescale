# Copyright (C) 2012 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.1"

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "9adf5e5c208989289075e6a5458a301a"
SRC_URI[sha256sum] = "41b75d937ef0e511b21e22d7ddbb419c99dcf358bc6f581edc2c9056729bfa9b"

COMPATIBLE_MACHINE = "(mx6)"
