# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/imx-gpu-viv/imx-gpu-viv-6.inc
SRC_URI[md5sum] = "83c40f63358dd3bd9bbc1cd7521bf8fe"
SRC_URI[sha256sum] = "5abfc3b24c1f9d02970064898fb30da705b67bc7e967dbfbf0525c1cc60f2491"

PACKAGE_FP_TYPE = "hardfp"

COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx6sx|mx6sl|mx7ulp)"
