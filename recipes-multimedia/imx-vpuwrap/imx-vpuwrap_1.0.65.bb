# Copyright (C) 2013-2016 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "imx-vpu"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=8cf95184c220e247b9917e7244124c5a"

# For backwards compatibility
PROVIDES += "libfslvpuwrap"
RREPLACES_${PN} = "libfslvpuwrap"
RPROVIDES_${PN} = "libfslvpuwrap"
RCONFLICTS_${PN} = "libfslvpuwrap"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "0bbcd6b9c9906fb85f98160eba94827b"
SRC_URI[sha256sum] = "ddeacb7d9cb26c8864374df6d3f090aad47cd2ae409c2b9c817b773447252d61"

inherit fsl-eula-unpack autotools pkgconfig

do_install_append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6q|mx6dl)"
