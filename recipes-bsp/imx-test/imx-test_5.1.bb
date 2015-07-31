# Copyright (C) 2013-2015 Freescale Semiconductor

include imx-test.inc

SRC_URI_append_mx5 = " file://clocks.sh"
SRC_URI_append_mxs = " file://clocks.sh"

SRC_URI[md5sum] = "5991439d4b20abac1095688164d26f40"
SRC_URI[sha256sum] = "042d0b03d7838395a6b35d0ca7159f1f4b6cf3055dcc4a12a1b66488fca987b1"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7)"
