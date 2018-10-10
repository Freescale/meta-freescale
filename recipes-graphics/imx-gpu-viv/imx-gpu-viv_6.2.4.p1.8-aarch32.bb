# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/imx-gpu-viv/imx-gpu-viv-6.inc
SRC_URI[md5sum] = "ef9e0b5fcb140c72b63dcf8b8da0be6b"
SRC_URI[sha256sum] = "7c31d1c7b45309ff7ca667b4d69b7c3b5fa320dcdcd90f2eb895f66f826f422b"

PACKAGE_FP_TYPE = "hardfp"

COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx6sx|mx6sl|mx7ulp)"
