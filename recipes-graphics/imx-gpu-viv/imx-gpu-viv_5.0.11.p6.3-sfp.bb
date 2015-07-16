# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-gpu-viv.inc

SRC_URI[md5sum] = "b2155c395de054184211fb57ccdb1dfb"
SRC_URI[sha256sum] = "9dad9944d7d3c4dd8cba52a829625c0546df0b15324e723c61e6f3ae97da9487"

# FIXME skip the QA error for viv-samples
INSANE_SKIP_${PN} += "rpaths"

PACKAGE_FP_TYPE = "softfp"
