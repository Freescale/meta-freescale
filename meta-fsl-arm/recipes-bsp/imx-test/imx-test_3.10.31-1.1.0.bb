# Copyright (C) 2013, 2014 Freescale Semiconductor

include imx-test.inc

# FIXME: Drop 'beta' suffix for GA release
SRC_URI = "${FSL_MIRROR}/${PN}-${PV}-beta.tar.gz \
           file://Fix-build-in-OpenEmbedded-Core-environment.patch"
S = "${WORKDIR}/${PN}-${PV}-beta"

SRC_URI_append_mx5 = " file://revert_epdc_hdr_change.patch \
                       file://clocks.sh"
SRC_URI_append_mxs = " file://revert_epdc_hdr_change.patch \
                       file://clocks.sh"

SRC_URI[md5sum] = "6170af41652f1f213d1a86340e103797"
SRC_URI[sha256sum] = "6aa33ef21932ca0349dbda05b6b151d5d705663180abc5f860670293251aef54"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6)"
