# Copyright (C) 2013, 2014 Freescale Semiconductor

include imx-test.inc

SRC_URI_append_mx5 = " file://revert_epdc_hdr_change.patch \
                       file://clocks.sh"
SRC_URI_append_mxs = " file://revert_epdc_hdr_change.patch \
                       file://clocks.sh"

SRC_URI[md5sum] = "3e066a84878b93ee52e54a040a7b2b61"
SRC_URI[sha256sum] = "21bedcbd707e392d8558ec5a73095ca15b4c95ab66deabb06876aaf3f8dac2c4"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6)"
