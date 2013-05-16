# Copyright (C) 2012-2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

SRC_URI[md5sum] = "08be9263c609b90e3b3e2e3aa888cd29"
SRC_URI[sha256sum] = "f3cd4a51d1eb60c21219d8d94daa832e47fc51d8f4618bb79cdf36bcca0a0f7c"

SRC_URI_append += "file://fix-missing-sys-types-h.patch \
                   file://Link-with-the-Real-Time-Extension-lib.patch \
                   file://vss_build_failed.patch \
                   file://configure.ac-Use-pkg-config-sysroot-when-checking-fo.patch \
                   file://v4lsink_back_compatible.patch \
                  "

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
