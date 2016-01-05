# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-gpu-viv.inc

SRC_URI[md5sum] = "6423b4a803106e7a3fff0911daaff410"
SRC_URI[sha256sum] = "3ea201f91ce9b56455e4983ab1055e8175415fbbf2800dc097d47e78ceefba34"

# FIXME skip the QA error for viv-samples
INSANE_SKIP_${PN} += "rpaths"

PACKAGE_FP_TYPE = "softfp"
