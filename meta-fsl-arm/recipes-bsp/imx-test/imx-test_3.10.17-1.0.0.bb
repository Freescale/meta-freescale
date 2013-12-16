# Copyright (C) 2013, 2014 Freescale Semiconductor

include imx-test.inc

# FIXME: Drop 'beta' suffix for GA release
SRC_URI = "${FSL_MIRROR}/${PN}-${PV}_beta.tar.gz"
S="${WORKDIR}/${PN}-${PV}_beta"

SRC_URI_append_mx5 = " file://revert_epdc_hdr_change.patch \
                       file://clocks.sh"
SRC_URI_append_mxs = " file://revert_epdc_hdr_change.patch \
                       file://clocks.sh"

SRC_URI[md5sum] = "fd3de6e882a7b5425853083ec8d4951e"
SRC_URI[sha256sum] = "1e86cfe800fbf2db2a52ce0155ab2d2ba70913a30974b3f66879198eaf9da21b"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6)"
