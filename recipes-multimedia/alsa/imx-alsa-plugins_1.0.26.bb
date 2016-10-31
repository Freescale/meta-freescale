# Copyright (C) 2013-2016 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale alsa-lib plugins"
LICENSE = "GPLv2"
SECTION = "multimedia"
DEPENDS = "alsa-lib virtual/kernel"

# For backwards compatibility
PROVIDES += "fsl-alsa-plugins"
RREPLACES_${PN} = "fsl-alsa-plugins"
RPROVIDES_${PN} = "fsl-alsa-plugins"
RCONFLICTS_${PN} = "fsl-alsa-plugins"

# Make sure kernel sources are available
do_configure[depends] += "virtual/kernel:do_shared_workdir"

LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "8fe4bcfddeca82dd01a9c4c6ce9471df"
SRC_URI[sha256sum] = "0a7e8d90fdde2f6780605ecfee674e6e13523915a7bcd64078bc507ac1a8deb8"

INCLUDE_DIR = "-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include"

EXTRA_OECONF = "CFLAGS="${INCLUDE_DIR}""

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} += "${libdir}/alsa-lib/libasound_*.so"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug"
FILES_${PN}-dev += "${libdir}/alsa-lib/*.la"

COMPATIBLE_MACHINE = "(mx6dl|mx6q|mx6sl|mx6sx|mx6ul|mx6ull|mx7d)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"
