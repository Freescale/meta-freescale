# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "imx-vpu"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=93b784b1c11b3fffb1638498a8dde3f6"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "1f50110cb6de8ebf767fb9c5f8baf20d"
SRC_URI[sha256sum] = "7fc1258de338339d19a1a35167393fdc4d773682dfd9b951b197403a075f85fd"

SRC_URI_append = " file://0001-vpu_wrapper-fix-tests-of-return-value-from-IOGetVirt.patch"

inherit fsl-eula-unpack autotools pkgconfig

do_install_append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}/imx-mm
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6q|mx6dl)"
