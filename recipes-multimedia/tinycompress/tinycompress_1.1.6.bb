DESCRIPTION = "A library to handle compressed formats like MP3 etc."
LICENSE = "LGPL-2.1-only | BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=cf9105c1a2d4405cbe04bbe3367373a0"
DEPENDS = "alsa-lib"

SRC_URI = "git://git.alsa-project.org/tinycompress.git;protocol=git;branch=master \
           file://0001-tinycompress-Add-id3-decoding.patch \
           file://0002-cplay-Support-wave-file.patch \
           file://0003-cplay-Add-pause-feature.patch \
           file://0004-tinycompress-pass-NULL-buffer-with-0-size-to-driver.patch \
           file://0005-cplay-Support-aac-streams.patch \
"
SRCREV = "995f2ed91045dad8c20485ab1a64727d22cd92e5"
S = "${WORKDIR}/git"

inherit autotools pkgconfig
