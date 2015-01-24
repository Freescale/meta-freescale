# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-gpu-viv.inc

SRC_URI[md5sum] = "386b66e25abfb962f8eaf54fa85e7c18"
SRC_URI[sha256sum] = "08349e3e6f23287f03e3a896c16938acc9cff44a1f6d624b23f7d348e09ab1ac"

# FIXME skip the QA error for viv-samples
INSANE_SKIP_${PN} += "rpaths"

PACKAGE_FP_TYPE = "softfp"
