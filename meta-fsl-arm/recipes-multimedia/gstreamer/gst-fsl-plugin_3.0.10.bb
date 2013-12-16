# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

EXTRA_OECONF += " CROSS_ROOT=${PKG_CONFIG_SYSROOT_DIR}"

SRC_URI = "${FSL_MIRROR}/gst-fsl-plugins-${PV}.tar.gz"
S = "${WORKDIR}/gst-fsl-plugins-${PV}"

SRC_URI[md5sum] = "681a5e97d621eaa5b1bcca2597cd0152"
SRC_URI[sha256sum] = "d1d194141c038ef72e7d4271355b2ef1a38bf5d110e93101609ab422596167df"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
