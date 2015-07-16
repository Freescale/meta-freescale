# Copyright (C) 2012-2015 Freescale Semiconductor
# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require xf86-video-imxfb-vivante.inc

SRC_URI = "${FSL_MIRROR}/xserver-xorg-video-imx-viv-${@'${PV}'.replace('5.0.11.p6.3', '5.0.11.p6.3-beta')}.tar.gz \
            file://rc.autohdmi"

SRC_URI[md5sum] = "2c1f0095a7e1ed5ca6cd070e9c8e2cff"
SRC_URI[sha256sum] = "660738bf0d75c71ee83b3d84c234a4304d85fde4a28b4a27fb9b59dbfcc94632"

S = "${WORKDIR}/xserver-xorg-video-imx-viv-${@'${PV}'.replace('5.0.11.p6.3', '5.0.11.p6.3-beta')}/"
