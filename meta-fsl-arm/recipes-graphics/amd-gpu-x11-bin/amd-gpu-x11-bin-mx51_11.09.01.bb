# Copyright (C) 2011-2015 O.S. Systems Software LTDA.
# Copyright (C) 2011, 2012 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for x11 on mx51"
PR = "r12"

# FIXME: Replace for correct AMD license
LIC_FILES_CHKSUM = "file://usr/include/VG/openvg.h;endline=30;md5=b0109611dd76961057d4c45ae6519802"
DEPENDS = "virtual/libx11 libxrender libxext"

include amd-gpu-mx51.inc

SRC_URI[md5sum] = "54391a4e670b597d06d01253fb217cad"
SRC_URI[sha256sum] = "c7a6fa03b7aa2a375556c59908876554ba720c1e744baba2debb84a408f790db"

RCONFLICTS_${PN} = "amd-gpu-bin-mx51"

COMPATIBLE_MACHINE = "${@base_contains('DISTRO_FEATURES', 'x11', '(mx5)', 'Invalid!', d)}"
