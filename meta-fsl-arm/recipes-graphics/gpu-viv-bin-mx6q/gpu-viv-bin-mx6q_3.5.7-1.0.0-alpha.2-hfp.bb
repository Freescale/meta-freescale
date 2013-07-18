# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include gpu-viv-bin-mx6q.inc

SRC_URI += "file://0001-change-header-path-to-HAL.patch \
            file://gc_hal_eglplatform-remove-xlib-undefs.patch"

SRC_URI[md5sum] = "f5f4e2e7767c784315461c132929e7d3"
SRC_URI[sha256sum] = "f16747ee5ae2e88631cc1494db98f7b5762940c7db25795906b7cc8f87405caf"

PACKAGE_FP_TYPE = "hardfp"
