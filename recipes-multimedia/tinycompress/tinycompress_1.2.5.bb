DESCRIPTION = "A library to handle compressed formats like MP3 etc."
LICENSE = "LGPL-2.1-only | BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=cf9105c1a2d4405cbe04bbe3367373a0"
DEPENDS = "alsa-lib"

inherit autotools pkgconfig

PV .= "+git"

SRC_URI = "git://github.com/alsa-project/tinycompress.git;protocol=https;branch=master \
"
SRCREV = "f3ba6e5c2126f2fb07e3d890f990d50c3e204e67"

EXTRA_OECONF:append = " --enable-pcm"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
