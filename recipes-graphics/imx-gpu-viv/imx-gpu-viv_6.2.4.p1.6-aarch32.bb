# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/imx-gpu-viv/imx-gpu-viv-6.inc

SRC_URI[md5sum] = "25f961c67d8c3b8b0f38bbf3b6da5ea6"
SRC_URI[sha256sum] = "463b3cba4b6f817e8f2b1abdadb51bbecdbdab3066a5c0b92504156a6f86f8c0"

PACKAGE_FP_TYPE = "hardfp"

COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx6sx|mx6sl|mx7ulp)"
