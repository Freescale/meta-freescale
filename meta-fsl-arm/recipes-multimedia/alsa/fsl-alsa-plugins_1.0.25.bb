# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale alsa-lib plugins"
LICENSE = "GPLv2"
SECTION = "multimedia"
DEPENDS = "alsa-lib virtual/kernel"

LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "b1ca7a250a8cd5da07062081b30b4118"
SRC_URI[sha256sum] = "902df92255d755e8eb08b3c3db0c7b9d70d26d9659b219373bee425ffdc34245"

INCLUDE_DIR = "-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include"

EXTRA_OECONF = "CFLAGS="${INCLUDE_DIR}""

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} += "${libdir}/alsa-lib/libasound_*.so"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug"
FILES_${PN}-dev += "${libdir}/alsa-lib/*.la"

COMPATIBLE_MACHINE = "(mx6)"
