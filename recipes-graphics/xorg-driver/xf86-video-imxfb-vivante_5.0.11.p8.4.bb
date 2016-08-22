# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require xf86-video-imxfb-vivante.inc

SRC_URI += "file://Stop-using-Git-to-write-local-version.patch"

SRC_URI[md5sum] = "7a2c8d4e56c33b9692d252193f00ef44"
SRC_URI[sha256sum] = "ddb6de9e00ce0b22f6c905eaf6694424413eade53ef6cd3b36e20ac99dcc0e0f"

PR = "r1"
