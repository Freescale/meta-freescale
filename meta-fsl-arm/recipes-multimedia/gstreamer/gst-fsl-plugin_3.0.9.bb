# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

# FIXME: Inspecting the source code the content is in fact 3.0.9
SRC_URI = "${FSL_MIRROR}/gst-fsl-plugins-3.10.9-1.0.0.tar.gz \
           file://remove-GST_INFO-in-mfw_gst_utils.h.patch \
           file://makefile.am-fix-aac-for-imx6.patch \
           file://configure.ac-Fix-checking-for-include-headers-in-mul.patch"

SRC_URI[md5sum] = "a338a5c25225c765ae5f44af82fe7413"
SRC_URI[sha256sum] = "e809daa0cd60381f05a79f34a53305a5ee53bfed22192a1cda6d998acc84a6fd"

S = "${WORKDIR}/gst-fsl-plugins-3.10.9-1.0.0"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
