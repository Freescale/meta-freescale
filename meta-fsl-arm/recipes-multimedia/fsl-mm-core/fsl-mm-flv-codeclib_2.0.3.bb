# Copyright (C) 2011-2013 Freescale Semicondutors <aalonso@freescale.com>
# Released under the MIT license (see COPYING.MIT for the terms)

include fsl-mm-codeclib.inc

PR = "${INC_PR}.0"
LIC_FILES_CHKSUM = "file://ghdr/common/fsl_types.h;endline=13;md5=b805ce4a390c94d002ef86bd15ceafd4"

SRC_URI[md5sum] = "b0244d881f73557be9221e6534c239be"
SRC_URI[sha256sum] = "737399c76e8b991ebb76c07599b83da6a185211d88b95d9a9b4245f6d7fccec2"

do_install_append() {
    # FIXME: Those files are deployed in fsl-mm-codeclib
    rm -r ${D}${includedir}/mm_ghdr/common
}

# FIXME: Same pkgconfig file is provided in every source so we need to
#        depends on 'fsl-mm-codeclib-dev' explicitly as it is
#        installed just on it.
RDEPENDS_${PN}-dev = "fsl-mm-codeclib-dev"

COMPATIBLE_MACHINE = "(mx5)"
