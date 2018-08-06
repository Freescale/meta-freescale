# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "virtual/imxvpu"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab61cab9599935bfe9f700405ef00f28"

# For backwards compatibility
PROVIDES += "libfslvpuwrap"
RREPLACES_${PN} = "libfslvpuwrap"
RPROVIDES_${PN} = "libfslvpuwrap"
RCONFLICTS_${PN} = "libfslvpuwrap"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "dc69b4df71bca964cccb19a9b46de591"
SRC_URI[sha256sum] = "5adb17386584bdd39faa5004b4ceb83987f1519acf4466e15b42672def66f49d"

inherit fsl-eula-unpack autotools pkgconfig

do_install_append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

INSANE_SKIP_imx-vpuwrap = "file-rdeps"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx8mq)"
