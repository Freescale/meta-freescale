# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale alsa-lib plugins"
LICENSE = "GPLv2"
SECTION = "multimedia"
DEPENDS = "alsa-lib virtual/kernel"

# Make sure kernel sources are available
do_configure[depends] += "virtual/kernel:do_shared_workdir"

LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "b1ca7a250a8cd5da07062081b30b4118"
SRC_URI[sha256sum] = "902df92255d755e8eb08b3c3db0c7b9d70d26d9659b219373bee425ffdc34245"

SRC_URI_append_mx6 = " file://0001-asrc_pair-update-output-buffer-size.patch"
SRC_URI_append_mx6ul = " file://0001-asrc_pair-update-output-buffer-size.patch"
SRC_URI_append_mx7 = " file://0001-asrc_pair-update-output-buffer-size.patch"

INCLUDE_DIR = "-I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include"

EXTRA_OECONF = "CFLAGS="${INCLUDE_DIR}""

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} += "${libdir}/alsa-lib/libasound_*.so"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug"
FILES_${PN}-dev += "${libdir}/alsa-lib/*.la"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx6ul = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_SOCARCH}"
