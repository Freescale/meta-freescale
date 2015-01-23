# Copyright (C) 2013-2015 Freescale Semiconductor

include imx-test.inc

SRC_URI += "file://Fix-build-in-OpenEmbedded-Core-environment.patch"

SRC_URI_append_mx5 = " file://revert_epdc_hdr_change.patch \
                       file://clocks.sh"
SRC_URI_append_mxs = " file://revert_epdc_hdr_change.patch \
                       file://clocks.sh"

SRC_URI[md5sum] = "9a866ceb8c4f5fd2b3bdc7f054544987"
SRC_URI[sha256sum] = "a578c773ec3314572e56e8c5984dc5aec9d7195958d4aae47c225df8ca0fbdd7"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6)"
