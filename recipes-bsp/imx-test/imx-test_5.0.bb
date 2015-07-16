# Copyright (C) 2013-2015 Freescale Semiconductor

include imx-test.inc

SRC_URI_append_mx5 = " file://clocks.sh"
SRC_URI_append_mxs = " file://clocks.sh"

SRC_URI[md5sum] = "9a44d97f9133cfb933351cf57fe68bb1"
SRC_URI[sha256sum] = "d78c3c0c5f4b74e4efb625b030cd2bb9499143358140e64d2f4f0bba10904a9c"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7)"
