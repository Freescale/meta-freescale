# Copyright (C) 2011-2013 Freescale Semicondutors <aalonso@freescale.com>
# Released under the MIT license (see COPYING.MIT for the terms)

include fsl-mm-codeclib.inc

PR = "${INC_PR}.0"

LIC_FILES_CHKSUM = "file://ghdr/mp3_enc_interface.h;endline=11;md5=545a1927139b4739d8980c49954b6b95"

SRC_URI[md5sum] = "049611d9f76b524b9fa9521527ba3235"
SRC_URI[sha256sum] = "e3712fa45a8f42617773678d45ab7ae0f20150a4d904f1e73afa8baa1941cf99"

# FIXME: Same pkgconfig file is provided in every source so we need to
#        depends on 'fsl-mm-codeclib-dev' explicitly as it is
#        installed just on it.
RDEPENDS_${PN}-dev += "fsl-mm-codeclib-dev"

COMPATIBLE_MACHINE = "(mx5)"
