# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require gpu-viv-bin-mx6q.inc

SRC_URI[md5sum] = "8e8719c2e135c2524817a62002eec0e1"
SRC_URI[sha256sum] = "fd85593186f6b66a26e538edf6279034741a96d2f4ceb6108deb2fa5c35c962d"

# FIXME skip the QA error for viv-samples
INSANE_SKIP_${PN} += "rpaths"

PACKAGE_FP_TYPE = "softfp"
