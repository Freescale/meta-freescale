# Copyright (C) 2012-2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.0"
PE = "1"

include gpu-viv-bin-mx6q.inc

SRC_URI[md5sum] = "2bb7d2f4bdff79ae99ce0c9fc2540701"
SRC_URI[sha256sum] = "48d04d11c6fec11411bcd97c47199caea517ebcd86db6c70f1964b3358a68924"

# FIXME: 3.0.35-4.0.0 BSP release uses DirectFB 1.4 and Yocto has 1.6 so
# disable it for now
USE_DFB = "no"
