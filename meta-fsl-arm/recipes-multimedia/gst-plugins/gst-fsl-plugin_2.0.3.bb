# Copyright (C) 2011, 2012 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

PR = "${INC_PR}.1"

SRC_URI += "file://fix_segment_fault_in_v4lsink_for_yocto.patch"
SRC_URI[md5sum] = "036a8e86031b0670f41b10796e268f9e"
SRC_URI[sha256sum] = "ee024e6fe94ce309b10dc89ab247d1bbcf8ae9cc8006178c96101ce2d4d164a0"

COMPATIBLE_MACHINE = "(mx5)"
