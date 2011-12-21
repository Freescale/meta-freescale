# Copyright (C) 2011 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc
inherit autotools pkgconfig

DESCRIPTION = "Gstreamer freescale plugins"
LICENSE = "GPLv2 & LGPLv2 & LGPLv2.1"
SECTION = "multimedia"
DEPENDS = "gstreamer gst-plugins-base fsl-mm-codeclib imx-lib"
RDEPENDS_${PN} = "fsl-mm-codeclib"
PR = "r1"

LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
                    file://COPYING-LGPL-2;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
                    file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

SRC_URI = "file://${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "036a8e86031b0670f41b10796e268f9e"
SRC_URI[sha256sum] = "ee024e6fe94ce309b10dc89ab247d1bbcf8ae9cc8006178c96101ce2d4d164a0"

# Todo add a mechanism to map posible build targets
EXTRA_OECONF = "PLATFORM=MX51 --disable-valgrind --disable-examples --disable-debug"
