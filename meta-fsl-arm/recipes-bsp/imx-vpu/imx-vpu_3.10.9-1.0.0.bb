# Copyright (C) 2013 Freescale Semiconductor
require imx-vpu.inc

PE = "1"

SRC_URI += "file://build-Allow-CC-and-AR-to-be-overriden.patch"

SRC_URI[md5sum] = "61331c9ed2d4c1b3aeab9c35fd034ac3"
SRC_URI[sha256sum] = "5dd86a26c8d3013e0d78308b1ccd8815730818e465787f55013b492d2d3c7710"

COMPATIBLE_MACHINE = "(mx6)"
