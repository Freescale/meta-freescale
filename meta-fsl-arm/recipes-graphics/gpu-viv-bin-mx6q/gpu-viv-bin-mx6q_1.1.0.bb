# Copyright (C) 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.0"
PE = "1"

include gpu-viv-bin-mx6q.inc

SRC_URI[md5sum] = "60f4ba65f557fc63fde6dacfeef205a8"
SRC_URI[sha256sum] = "4238b72a6dad2d075d159bb1e86fb68bbed7c27894ce82c546a8e7c58ae5d683"

# FIXME: 1.1.0 BSP release uses DirectFB 1.4 and Yocto has 1.6 so
# disable it for now
USE_DFB = "no"
