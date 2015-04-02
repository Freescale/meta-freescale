# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-gpu-viv.inc

SRC_URI[md5sum] = "201398ab011b8765755fafb898efa77d"
SRC_URI[sha256sum] = "8eef5414ec9121b38e2e44b3b64705b11f6f5d4503b90c05c31e3990c6ca3999"

# FIXME skip the QA error for viv-samples
INSANE_SKIP_${PN} += "rpaths"

PACKAGE_FP_TYPE = "softfp"
