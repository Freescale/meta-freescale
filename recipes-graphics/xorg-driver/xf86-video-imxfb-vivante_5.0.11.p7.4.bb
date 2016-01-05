# Copyright (C) 2012-2015 Freescale Semiconductor
# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require xf86-video-imxfb-vivante.inc

SRC_URI += "file://Stop-using-Git-to-write-local-version.patch \
            file://Remove-dix-internal-header-usage.patch"

SRC_URI[md5sum] = "6e2d8ee518e9eef8421e9dcdc1ea79c3"
SRC_URI[sha256sum] = "3effaf42bde64da8fad2dfe0dcef12d1cfbbbe189511dbd4612442129fd832cc"
