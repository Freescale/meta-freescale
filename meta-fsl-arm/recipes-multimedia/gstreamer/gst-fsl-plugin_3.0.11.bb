# Copyright (C) 2013-14 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

EXTRA_OECONF += " CROSS_ROOT=${PKG_CONFIG_SYSROOT_DIR}"

SRC_URI[md5sum] = "0ed858681a74857034c006036023e6ce"
SRC_URI[sha256sum] = "fbc6000b401ac2c8d67d1414372f4a929cf0a5808f6ed1640f1d2bfcce2f2a4f"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
