# Copyright (C) 2013-2016 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "imx-vpu"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=d4f548f93b5fe0ee2bc86758c344412d"

# For backwards compatibility
PROVIDES += "libfslvpuwrap"
RREPLACES_${PN} = "libfslvpuwrap"
RPROVIDES_${PN} = "libfslvpuwrap"
RCONFLICTS_${PN} = "libfslvpuwrap"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "af5a6b93ff24fdb4bf1a31d6e831f526"
SRC_URI[sha256sum] = "282e7f8766ce385d8752bd29f04ddeff709ece0846be97547cf982183bbe241e"

inherit fsl-eula-unpack autotools pkgconfig

do_install_append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6q|mx6dl)"
