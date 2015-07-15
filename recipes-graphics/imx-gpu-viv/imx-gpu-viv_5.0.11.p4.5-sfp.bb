# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-gpu-viv.inc

SRC_URI[md5sum] = "479dce20e0e2f9f7d0a4e4ff70d4a4b2"
SRC_URI[sha256sum] = "b5b9c8e216b2bc4281c2443e07eab90547de0abd705614756ed68c7ad2fcf97a"

# FIXME skip the QA error for viv-samples
INSANE_SKIP_${PN} += "rpaths"

PACKAGE_FP_TYPE = "softfp"
