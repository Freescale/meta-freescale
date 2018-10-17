# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "virtual/imxvpu"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ab1a30d0cd181e3408077727ea5a2db"

# For backwards compatibility
PROVIDES += "libfslvpuwrap"
RREPLACES_${PN} = "libfslvpuwrap"
RPROVIDES_${PN} = "libfslvpuwrap"
RCONFLICTS_${PN} = "libfslvpuwrap"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "86d1829ef62ab51e187479bfea686874"
SRC_URI[sha256sum] = "85f01ebdf55e4bd7a7cc21eb5203dfcaa7b663f03ecdb8d5e54fbb995e3dc761"

inherit fsl-eula-unpack autotools pkgconfig

do_install_append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

INSANE_SKIP_imx-vpuwrap = "file-rdeps"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(imxvpu)"
