# Copyright (C) 2020 Mihai Lindner <mihai.lindner@nxp.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Sound Open Firmware"
HOMEPAGE = "https://www.sofproject.org"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENCE;md5=0f00d99239d922ffd13cabef83b33444"

SRC_URI = "${FSL_MIRROR}/sof-imx-${PV}.tar.gz"
SRC_URI[md5sum] = "c03aa6a07570b2e7b8f60ab859b8f24a"
SRC_URI[sha256sum] = "d6fecb5f398ecce4fefb7f98a35c9c2741735ccc4668d676bcf53b1d4ebbe778"

S = "${WORKDIR}/sof-imx-${PV}"

inherit allarch

do_install() {
    # Install sof and sof-tplg folder
    install -d ${D}${nonarch_base_libdir}/firmware/imx/
    cp -r sof* ${D}${nonarch_base_libdir}/firmware/imx/
}

FILES_${PN} = "${nonarch_base_libdir}/firmware/imx"
