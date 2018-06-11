# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/imx-gpu-viv/imx-gpu-viv-6.inc

SRC_URI[md5sum] = "280990aab8dee2ee9ce508cbf0d6833f"
SRC_URI[sha256sum] = "dddadd164bede4793409ccfb636324dd73862c33458db66a5860f126bada25dc"

PACKAGE_FP_TYPE = "hardfp"

COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx6sx|mx6sl|mx7ulp)"
