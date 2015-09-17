# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-gpu-viv.inc

SRC_URI[md5sum] = "5dbe194e87e4092379b195e866cdb687"
SRC_URI[sha256sum] = "43fea6ebbb1222e0f594fff3b48147f4bb6173b85f2caa9fa6bf04a1c85ee93a"

# FIXME skip the QA error for viv-samples
INSANE_SKIP_${PN} += "rpaths"

PACKAGE_FP_TYPE = "softfp"
