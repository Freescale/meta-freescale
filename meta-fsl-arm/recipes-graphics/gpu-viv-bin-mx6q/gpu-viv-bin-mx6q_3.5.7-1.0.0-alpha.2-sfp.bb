# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include gpu-viv-bin-mx6q.inc

SRC_URI += "file://0001-change-header-path-to-HAL.patch \
            file://gc_hal_eglplatform-remove-xlib-undefs.patch"

SRC_URI[md5sum] = "ef08a4ad04c7886a44af16e826d0f444"
SRC_URI[sha256sum] = "e212e361044eb1f9089761e115e71b27dab67c832ebb1ddbc830c17d2b1e2fba"

PACKAGE_FP_TYPE = "softfp"
