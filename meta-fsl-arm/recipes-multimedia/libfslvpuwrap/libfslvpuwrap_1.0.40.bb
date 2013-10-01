# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "imx-vpu"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=93b784b1c11b3fffb1638498a8dde3f6"

# FIXME: Inspecting the source code the content is in fact 1.0.40
SRC_URI = "${FSL_MIRROR}/${PN}-3.10.9-1.0.0.bin;fsl-eula=true"
SRC_URI[md5sum] = "25891ef8d92e82d9b2e999a74a327971"
SRC_URI[sha256sum] = "84d610c478963e7b6a9660a38547b5365ca910159972b3860d7356aee33b9b41"

S = "${WORKDIR}/${PN}-3.10.9-1.0.0"

inherit fsl-eula-unpack autotools pkgconfig

do_install_append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}/imx-mm
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx6s)"
