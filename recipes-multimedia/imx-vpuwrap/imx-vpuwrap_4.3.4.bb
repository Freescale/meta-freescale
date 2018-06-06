# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "virtual/imxvpu"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=08fd295cce89b0a9c74b9b83ed74f671"

# For backwards compatibility
PROVIDES += "libfslvpuwrap"
RREPLACES_${PN} = "libfslvpuwrap"
RPROVIDES_${PN} = "libfslvpuwrap"
RCONFLICTS_${PN} = "libfslvpuwrap"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "bd832f70ef90dbbdc5a24e2a1eefc71f"
SRC_URI[sha256sum] = "cd49a4d9379c2d3ffd71a4c8e81dcb30097d43899d11ccaa77ec981dbdec596b"

inherit fsl-eula-unpack autotools pkgconfig

do_install_append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

INSANE_SKIP_imx-vpuwrap = "file-rdeps"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6q|mx6dl)"
