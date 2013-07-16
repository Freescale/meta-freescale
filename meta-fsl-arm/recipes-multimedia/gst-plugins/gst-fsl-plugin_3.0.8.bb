# Copyright (C) 2012-2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

SRC_URI[md5sum] = "fe298c831e107ae5b93df05c4c29bb65"
SRC_URI[sha256sum] = "6c3215f24dedabf97ad6a7bd5c8d088b01dc4cd4792c56e0df353ae03adb35ff"

# FIXME: Inspecting the source code the content is in fact 3.0.8
SRC_URI = "${FSL_MIRROR}/gst-fsl-plugins-3.5.7-1.0.0.tar.gz"
S = "${WORKDIR}/gst-fsl-plugins-3.5.7-1.0.0"

SRC_URI_append += "file://configure.ac-Use-pkg-config-sysroot-when-checking-fo.patch"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
